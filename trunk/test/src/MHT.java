import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class MHT {
	public static void main(String[] args) throws Exception {
//		long time = System.nanoTime();
//		String s = "<title>12=E5=B9=B4=E7=AD=89=E5=BE=85=EF=BC=8C=E7=BB=88=E6=88=90=E7=8E=B0=E5=";
//		String s = "77u/PCFET0NUWVBFIEhUTUwgUFVCTElDICItLy9XM0MvL0RURCBIVE1MIDQuMCBUcmFuc2l0aW9u\r\nYWwvL0VOIj4NCjxIVE1MPjxIRUFEPjxUSVRMRT4xMuW5tOetieW+he+8jOe7iOaIkOeOsOWunuKA\r\nlOKAlOawuOi/nOWcsO+8jFN0YW5kIEJ5IE1l77yM5a6M576O55qE5a6M57uT77yBIC0g5q+F5Yqb\r\n5bCx5piv6K+05Yiw5YGa5Yiw77yB5YWR546w5om/6K+655qE5Yqq5Yqb5bGLIC0g5q2q6YW35Y2a";
//		String s = "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAMAAAAoLQ9TAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJ\r\nbWFnZVJlYWR5ccllPAAAADNQTFRF4GNj88PDzAEB5Hh40yQk1jEx+eDg7qio0RkZzQQEzgkJ3FBQ\r\nzxAQ6Y+P2UBAzAAA////2BZsTAAAAGlJREFUeNqEj9sKgEAIRN1LbRed8f+/thW2gog6DwqHUVT8\r\ngbwIdDbmaLCREJKKco0I88qFbEOAuSnRtYWYJ63Ju/CyaImE4SaFSBaQe6/z2EF30q7DhqiA+Gfi\r\nFAlVFVCdEDPy++0hwABOSA3gnXS3jgAAAABJRU5ErkJggg==";
//		String s = "FAlVFVCdEDPy++0hwABOSA3gnXS3jgAAAABJRU5ErkJggg==";
//		System.out.println("'"+decodeQ(s, "UTF-8")+"'");
//		System.out.println(new String(new BASE64Decoder().decodeBuffer(s)));
//		System.out.println(new String(decodeB(s.getBytes())));
//		System.out.println(System.nanoTime()-time);
//		System.out.println(new MHT("ie.mht"));
//		System.out.println(new MHT("unmht.mht"));
//		new MHT("ie.mht").decode();
//		new MHT("unmht.mht").decode();
		new MHT("ie.mht").save();
		new MHT("unmht.mht").save();
//		new MHT("1.mht").save();
//		new MHT("2.mht").save();
//		new MHT("3.mht").save();
//		new MHT("4.mht").save();
//		new MHT("5.mht").save();
//		new MHT("6.mht").save();
//		new MHT("7.mht").save();
//		new MHT("8.mht").save();
//		new MHT("9.mht").save();
//		System.out.println("dsf<base href=\"http://tancochan.ycool.com/\" />grw".replaceAll("<base\\s+href\\s*=\\s*\".*\".*((/>)|(</base>))", ""));
	}
	
	private static enum Transfer_Encoding {Null, Base64, QuotedPrintable};
	private static final byte[] NEWLINES = System.getProperty("line.separator", "\r\n").getBytes();
	private boolean isDecode;
	private String boundary;
	private String content;
	private String url;
	private Map<String, Entity> entityMap = new HashMap<String, Entity>();
	private File file;
	private String lastReadTempString;
	private String md5;
	
	public MHT(String fileName) {
		this(fileName, false);
	}
	
	public MHT(String fileName, boolean decode) {
		file = new File(fileName);
		if(decode) decode();
	}
	
	public boolean decode() {
		if(isDecode) return false;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));
			setBoundary(in);
			splitEntity(in, boundary);
			if(url!=null && content!=null)
				entityMap.remove(url);
			isDecode = true;
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(in!=null) try {
				in.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public void setBoundary(BufferedReader in) throws Exception {
		String temp;
		while((temp=in.readLine())!=null) {
			int i = temp.indexOf("boundary");
			if(i>-1) {
				int start = temp.indexOf("\"", i);
				int end = 0;
				if(start>-1) {
					start++;
					end = temp.indexOf("\"", start);
				} else {
					start = temp.indexOf("=", i);
					start++;
					end = temp.length();
				}
				boundary = temp.substring(start, end);
				return;
			}
		}
	}
	
	public void splitEntity(BufferedReader in, String boundary) throws Exception {
		if(boundary==null) return;
		lastReadTempString = in.readLine();
		while(lastReadTempString != null) {
			if(lastReadTempString.startsWith(boundary, 2) && !lastReadTempString.endsWith("--")) {
				try {
					addEntity(in, boundary);
				} catch(Exception e) {
					e.printStackTrace();
					lastReadTempString = in.readLine();
				}
			} else {
				lastReadTempString = in.readLine();
			}
		}
	}
	
	public void addEntity(BufferedReader in, String boundary) throws Exception {
		Entity entity = new Entity();
		try {
			while((lastReadTempString = in.readLine())!=null) {
				if(entity.type==null && lastReadTempString.startsWith("Content-Type: ")) {
					if(lastReadTempString.contains("multipart")) {
						while((lastReadTempString=in.readLine())!=null) {
							if(lastReadTempString.length()==0) throw new Exception("unknown mht format");
							char c = lastReadTempString.charAt(0);
							if(c=='\t' || c==' ') {
								int i = lastReadTempString.indexOf("boundary");
								if(i>-1) {
									int start = lastReadTempString.indexOf("\"", i);
									int end = 0;
									if(start>-1) {
										start++;
										end = lastReadTempString.indexOf("\"", start);
									} else {
										start = lastReadTempString.indexOf("=", i)+1;
										end = lastReadTempString.length();
									}
									splitEntity(in, lastReadTempString.substring(start, end));
									return;
								}
							} else {
								throw new Exception("unknown mht format");
							}
						}
					}
					int i = lastReadTempString.indexOf(";");
					if(i==-1) {
						entity.type = lastReadTempString.substring(14);
						continue;
					} else {
						entity.type = lastReadTempString.substring(14, i);
						i = lastReadTempString.indexOf("charset", i);
						if(i>-1) {
							i = lastReadTempString.indexOf("\"", i)+1;
							entity.charset = lastReadTempString.substring(i, lastReadTempString.indexOf("\"", i));
							continue;
						}
						while((lastReadTempString=in.readLine())!=null) {
							if(lastReadTempString.length()==0) break;
							char c = lastReadTempString.charAt(0);
							if(c=='\t' || c==' ') {
								i = lastReadTempString.indexOf("charset");
								if(i>-1) {
									i = lastReadTempString.indexOf("\"", i)+1;
									entity.charset = lastReadTempString.substring(i, lastReadTempString.indexOf("\"", i));
									lastReadTempString=in.readLine();
									break;
								}
							} else {
								break;
							}
						}
					}
				}
				if(entity.transferEncoding==null && lastReadTempString.startsWith("Content-Transfer-Encoding: ")) {
					entity.transferEncoding = lastReadTempString.substring(27);
				} else if(entity.location==null && lastReadTempString.startsWith("Content-Location: ")) {
					entity.location = lastReadTempString.substring(18);
				} else if(lastReadTempString.length()==0) break;
			}
			if(entity.location!=null) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				Transfer_Encoding transferEncoding;
				if("base64".equalsIgnoreCase(entity.transferEncoding)) transferEncoding = Transfer_Encoding.Base64;
				else if("quoted-printable".equalsIgnoreCase(entity.transferEncoding)) transferEncoding = Transfer_Encoding.QuotedPrintable;
				else transferEncoding = Transfer_Encoding.Null;
				while((lastReadTempString=in.readLine())!=null) {
					if(lastReadTempString.startsWith(boundary, 2)) break;
					switch(transferEncoding) {
					case Base64:
						out.write(MHTUtil.decodeB(lastReadTempString.getBytes()));
						break;
					case QuotedPrintable:
						out.write(MHTUtil.decodeQ(lastReadTempString.getBytes()));
						if(!lastReadTempString.endsWith("=")) out.write(NEWLINES);
						break;
					default:
						out.write(lastReadTempString.getBytes());
						out.write(NEWLINES);
						break;
					}
				}
				out.close();
				entity.data = out.toByteArray();
			}
		} finally {
			if(content==null && entity.type!=null && entity.type.contains("text")) {
				url = entity.location;
				if(entity.charset!=null) content = new String(entity.data, entity.charset);
				else content = new String(entity.data);
			}
			if(entity.location!=null && entity.data!=null && !entityMap.containsKey(entity.location)) entityMap.put(entity.location, entity);
		}
	}
	
	public String save(String dirPath) throws IOException {
		if(md5==null) {
			int length = (int)file.length();
			if(length==0) {
				md5 = "_0";
			} else {
				try {
					MessageDigest digest = MessageDigest.getInstance("MD5");
					StringBuilder sb = new StringBuilder("_");
					for(byte b:digest.digest((file.getPath()+":"+file.length()+":"+file.lastModified()).getBytes())) {
						sb.append(Integer.toHexString(0xFF & b));
					}
					md5 = sb.toString();
				} catch(NoSuchAlgorithmException e) {
					e.printStackTrace();
					md5 = "_e";
				}
			}
		}
		String fileName = file.getName();
		int i = fileName.lastIndexOf('.');
		if(i>0) fileName = fileName.substring(0, i);
		fileName += md5;
		if(dirPath==null) dirPath = "";
		if(dirPath.length()>0 && !dirPath.equals(File.separatorChar)) dirPath+=File.separatorChar;
		File file = new File(dirPath+fileName+".html");
		if(file.isFile()) return file.getAbsolutePath();
		else if(file.exists()) {
			fileName = fileName + "_" + System.currentTimeMillis();
			file = new File(dirPath+fileName+".html");
		}
		if(!isDecode) decode();
		if(content==null) content = "";
		if(entityMap.size()>1) {
			dirPath = dirPath+fileName+File.separatorChar;
			File dir = new File(dirPath);
			dir.mkdirs();
			dirPath = dir.getName()+"/";
			Iterator<Entry<String, Entity>> iterator = entityMap.entrySet().iterator();
			List<String> entityNameList = new ArrayList<String>();
			while(iterator.hasNext()) {
				Entity entity = iterator.next().getValue();
				int i1 = entity.location.indexOf("?");
				int i2;
				if(i1>-1) i2 = entity.location.lastIndexOf("/", i1);
				else {
					i1 = entity.location.length();
					i2 = entity.location.lastIndexOf("/");
				}
				String entityName = (i2>-1?entity.location.substring(i2+1, i1):"");
				if(entityName.length()==0) entityName = String.valueOf(System.currentTimeMillis());
				entityName = entityName.toLowerCase();
				while(entityNameList.contains(entityName)) {
					entityName = entityName+"_"+System.nanoTime();
				}
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(dir, entityName)));
				out.write(entity.data);
				out.close();
				content = content.replace(entity.location, dirPath+entityName);
			}
		}
		content = content.replaceAll("<base\\s+href\\s*=\\s*\".*\".*((/>)|(</base>))", "");
		if(url!=null) content = content.replace(url, file.getName());
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		out.write(content.getBytes());
		out.close();
		return file.getAbsolutePath();
	}
	
	public String save() throws IOException {
		return save(file.getParent());
	}
	
	private class Entity {
		private String type;
		private String charset;
		private String transferEncoding;
		private String location;
		private byte[] data;
	}
}
