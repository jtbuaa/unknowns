package hld.coins.status;

import hld.coins.interfaces.AbstractGameStatus;
import hld.coins.manager.GameViewManager;
import hld.coins.view.LoadingView;
import android.app.Activity;

public class LoadingStatus extends AbstractGameStatus {
	@Override
	public void EntryCurrentStatus(Activity activity) {
		GameViewManager.getInstance().removeAllView();
		new LoadingView();
	}
	
	@Override
	public void RemoveCurrentStatus(Activity activity) {}
}
