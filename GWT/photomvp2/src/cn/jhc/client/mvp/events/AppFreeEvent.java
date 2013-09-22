package cn.jhc.client.mvp.events;

import com.google.gwt.event.shared.GwtEvent;

public class AppFreeEvent extends GwtEvent<AppFreeHandler> {
	
	private static Type<AppFreeHandler> TYPE = new Type<AppFreeHandler>();
	
	public AppFreeEvent() {}
	
	public static Type<AppFreeHandler> getType() {
		return TYPE;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AppFreeHandler> getAssociatedType() {
		return TYPE;
		
	}

	public void fire(HasAppFreeHandlers source) {
		source.fireEvent(new AppFreeEvent());
	}
	@Override
	protected void dispatch(AppFreeHandler handler) {
		handler.onAppFreeEvent(this);
	}


}
