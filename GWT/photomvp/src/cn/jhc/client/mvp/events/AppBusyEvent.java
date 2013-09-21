package cn.jhc.client.mvp.events;

import com.google.gwt.event.shared.GwtEvent;

public class AppBusyEvent extends GwtEvent<AppBusyHandler> {

	private static Type<AppBusyHandler> TYPE = new Type<AppBusyHandler>();
	
	public AppBusyEvent() {}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AppBusyHandler> getAssociatedType() {
		return TYPE;
		
	}

	public static Type<AppBusyHandler> getType(){
		return TYPE;
	}
	@Override
	protected void dispatch(AppBusyHandler handler) {
		handler.onAppBusyEvent(this);
	}
	
	public static void fire(HasAppBusyHandlers source) {
		source.fireEvent(new AppBusyEvent());
	}

}
