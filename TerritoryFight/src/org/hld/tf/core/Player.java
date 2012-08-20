package org.hld.tf.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.hld.tf.card.base.Figure;
import org.hld.tf.card.base.Territory;

/**
 * 玩家
 */
public class Player {
	private List<Figure> figures = new ArrayList<Figure>();
	private List<Territory> territories = new ArrayList<Territory>();
	private String id;
	private String name;
	
	public Player(String name) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * 返回拥有的人物卡
	 * @return
	 */
	public List<Figure> getFigure() {
		return figures;
	}
	
	/**
	 * 返回拥有的人物卡数
	 * @return
	 */
	public int getFigureCount() {
		return figures.size();
	}
	
	/**
	 * 获取一张人物卡
	 * @param card
	 */
	public void drawFigure(Figure card) {
		figures.add(card);
	}
	
	/**
	 * 打出一张人物卡
	 * @param type
	 * @return
	 */
	public Figure playFigure(Class<? extends Figure> type) {
		for(int i = 0; i<figures.size(); i++) {
			if(type.isInstance(figures.get(i))) {
				return figures.remove(i);
			}
		}
		return null;
	}
	
	/**
	 * 弃一张人物卡
	 */
	public Figure discardFigure() {
		if(!figures.isEmpty()) {
			return figures.remove(new Random().nextInt(figures.size()));
		}
		return null;
	}
	
	/**
	 * 判断是否有指定人物卡
	 * @param type
	 * @return
	 */
	public boolean checkFigure(Class<? extends Figure> type) {
		for(Figure character:figures) {
			if(type.isInstance(character)) return true;
		}
		return false;
	}
	
	/**
	 * 返回拥有的领土卡
	 * @return
	 */
	public List<Territory> getTerritories() {
		return territories;
	}
	
	/**
	 * 获取一张领土卡
	 * @param card
	 */
	public void drawTerritory(Territory card) {
		territories.add(card);
		Game.checkGameWinner(this);
	}
	
	/**
	 * 弃一张领土卡
	 */
	public Territory discardTerritory() {
		if(!territories.isEmpty()) {
			return territories.remove(new Random().nextInt(territories.size()));
		}
		return null;
	}
	
	/**
	 * 消灭指定的地
	 * @param type
	 * @return
	 */
	public Territory wipeOutTerritory(Class<? extends Territory> type) {
		Iterator<Territory> iterator = territories.iterator();
		while(iterator.hasNext()) {
			Territory territory = iterator.next();
			if(type.isInstance(territory)) {
				if(territory.canWipeOut()) {
					iterator.remove();
					return territory;
				} else {
					break;
				}
			}
		}
		return null;
	}
	
	/**
	 * 牺牲指定的地
	 * @param type
	 * @return
	 */
	public Territory sacrificeTerritory(Class<? extends Territory> type) {
		Iterator<Territory> iterator = territories.iterator();
		while(iterator.hasNext()) {
			Territory territory = iterator.next();
			if(type.isInstance(territory)) return territory;
		}
		return null;
	}
	
	/**
	 * 判断是否有指定领土卡
	 * @param type
	 * @return
	 */
	public boolean checkTerritory(Class<? extends Territory> type) {
		for(Territory territory:territories) {
			if(type.isInstance(territory)) return true;
		}
		return false;
	}
	
	/**
	 * 判断指定领土卡一共有多少张
	 * @param type
	 * @return
	 */
	public int checkTerritoryCount(Class<? extends Territory> type) {
		int i = 0;
		for(Territory territory:territories) {
			if(type.isInstance(territory)) i++;
		}
		return i;
	}
	
	/**
	 * 判断现在的分数
	 * @return
	 */
	public int checkPoint() {
		int point = 0;
		for(Territory territory:territories) {
			point+=territory.getPoint();
		}
		return point;
	}
}