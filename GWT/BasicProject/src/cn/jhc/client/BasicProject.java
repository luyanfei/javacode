package cn.jhc.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SimpleHtmlSanitizer;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BasicProject implements EntryPoint, ValueChangeHandler<String> {

	static final int DECK_HOME = 0;
	static final int DECK_PRODUCTS = 1;
	static final int DECK_CONTACT = 2;
	
	static final String TOKEN_HOME = "home";
	static final String TOKEN_PRODUCTS = "products";
	static final String TOKEN_CONTACT = "contact";
	
	HTMLPanel homePanel;
	HTMLPanel productsPanel;
	HTMLPanel contactPanel;
	TabLayoutPanel content;
	Button search;
	FocusPanel feedback;
	Image logo;
	protected PopupPanel searchRequest;
	
	enum Pages {
		HOME(DECK_HOME, TOKEN_HOME), PRODUCTS(DECK_PRODUCTS, TOKEN_PRODUCTS), CONTACT(DECK_CONTACT, TOKEN_CONTACT);
		
		// Holds the card number in the deck this enumeration relates to. 
		private int val;
		// Holds the history token value this enumeration relates to.
		private String text;
		
		// Simple method to get the card number in the deck this enumeration relates to.
		int getVal(){return val;}
		// Simple method to get the history token this enumeration relates to.
		String getText(){return text;}

		// Enumeration constructor that stores the card number and history token for this enumeration.
		Pages(int val, String text) {
			this.val = val;
			this.text = text;
		};
	}

	@Override
	public void onModuleLoad() {
		setUpGui();
		setUpHistoryManagement();
		setUpEventHandling();
	}

	private void setUpEventHandling() {
		content.addSelectionHandler(new SelectionHandler<Integer>() {
			
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				Integer tabSelected = event.getSelectedItem();
				History.newItem(Pages.values()[tabSelected].getText());
			}
		});
		
		search.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				FlowPanel qAnswer;
				final TextBox searchTerm = new TextBox();
				
				if(searchRequest == null) {
					searchRequest = new PopupPanel();
					qAnswer = new FlowPanel();
					qAnswer.add(new Label("Search For:"));
					qAnswer.add(searchTerm);
					
					searchTerm.addChangeHandler(new ChangeHandler() {
						
						@Override
						public void onChange(ChangeEvent event) {
							searchRequest.hide();
							Window.alert("If implemented, now we would search for: "+searchTerm.getText());
						}
					});
					
					searchRequest.add(qAnswer);
					searchRequest.setAnimationEnabled(true);
					searchRequest.showRelativeTo(search);
					searchRequest.setAutoHideEnabled(true);
					searchRequest.setAutoHideOnHistoryEventsEnabled(true);
				}
				else {
					searchTerm.setText("");
					searchRequest.show();
				}
				searchTerm.setFocus(true);
			}
		});
		
		feedback.addMouseOverHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				feedback.removeStyleName("normal");
				feedback.addStyleName("active");
				RootPanel.getBodyElement().getStyle().setProperty("overflow", "hidden");
			}
		});
		
		feedback.addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				feedback.removeStyleName("active");
				feedback.addStyleName("normal");
				RootPanel.getBodyElement().getStyle().setProperty("overflow", "auto");
			}
		});
		
		feedback.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("You could provide feedback if this was implemented");
			}
		});
	}

	private void setUpHistoryManagement() {
		History.addValueChangeHandler(this);
		History.fireCurrentHistoryState();
		// Trap user hitting back button too many times.
		Window.addWindowClosingHandler(new ClosingHandler(){
			public void onWindowClosing(ClosingEvent event) {
				event.setMessage("Ran out of history.  Now leaving application, is that OK?");
			}
		});
	}

	private void setUpGui() {
		buildTabContent();
		wrapExistingSearchButton();
		insertLogo();
		createFeedbackTab();
		styleTabPanelUsingUIObject();
		styleButtonUsingDOM();
		RootPanel.get().add(feedback);

		RootPanel logoSlot = RootPanel.get("logo");
		if (logoSlot != null)
			logoSlot.add(logo);
		RootPanel contentSlot = RootPanel.get("content");
		if (contentSlot != null)
			contentSlot.add(content);

	}

	private void styleButtonUsingDOM() {
		search.getElement().getStyle()
				.setProperty("backgroundColor", "#ff0000");
		search.getElement().getStyle().setProperty("border", "2px solid");
		search.getElement().getStyle().setOpacity(0.7);
	}

	private void styleTabPanelUsingUIObject() {
		homePanel.setHeight("400px");
		productsPanel.setHeight("400px");
		contactPanel.setHeight("400px");
		content.setHeight("420px");
	}

	private void createFeedbackTab() {
		feedback = new FocusPanel();
		feedback.setStyleName("feedback");
		feedback.addStyleName("normal");

		VerticalPanel text = new VerticalPanel();
		text.add(new Label("Feed"));
		text.add(new Label("Back"));
		feedback.add(text);
	}

	private void insertLogo() {
		logo = new Image(GWT.getModuleBaseURL() + "../gwtia.png") {
			public void onBrowserEvent(com.google.gwt.user.client.Event event) {
				event.preventDefault();
				super.onBrowserEvent(event);
			}
		};

	}

	private void wrapExistingSearchButton() {
		Element element = DOM.getElementById("search");
		if (element != null) {
			search = Button.wrap(element);
		} else {
			GWT.log("The search button is missing.");
			search = new Button("search");
			RootPanel.get().add(search);
		}
	}

	private void buildTabContent() {
		homePanel = new HTMLPanel(getContent("home"));
		productsPanel = new HTMLPanel(getContent("products"));
		contactPanel = new HTMLPanel(getContent("contact"));

		content = new TabLayoutPanel(20, Unit.PX);

		content.add(homePanel, Pages.HOME.getText());
		content.add(productsPanel, Pages.PRODUCTS.getText());
		content.add(contactPanel, Pages.CONTACT.getText());
		content.selectTab(DECK_HOME);
	}

	private String getContent(String id) {
		String toReturnString = "";
		Element element = DOM.getElementById(id);
		if (element != null) {
			toReturnString = DOM.getInnerHTML(element);
			DOM.setInnerText(element, "");
			SafeHtml sfHtml = SimpleHtmlSanitizer.sanitizeHtml(toReturnString);
			toReturnString = sfHtml.asString();
		} else {
			toReturnString = "Unable to find " + id + "content in HTML page.";
		}
		return toReturnString;
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String page = event.getValue().trim();
		if ((page == null) || (page.equals("")))
			showHomePage();
		// Else check what the token is and cal the appropriate method.
		else if (page.equals(Pages.PRODUCTS.getText()))
			showProducts();
		else if (page.equals(Pages.CONTACT.getText()))
			showContact();
		else
			showHomePage();
	}

	/**
	 * Show the contact page - i.e. place a new label on the current screen
	 */
	private void showContact() {
		content.selectTab(Pages.CONTACT.getVal());
	}

	/**
	 * Show the home page - i.e. place a new label on the current screen
	 */
	private void showHomePage() {
		content.selectTab(Pages.HOME.getVal());
	}

	/**
	 * Show the products page - i.e. place a new label on the current screen
	 */
	private void showProducts() {
		content.selectTab(Pages.PRODUCTS.getVal());
	}
}
