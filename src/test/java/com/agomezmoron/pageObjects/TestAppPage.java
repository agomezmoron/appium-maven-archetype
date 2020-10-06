package com.agomezmoron.pageObjects;

import com.agomezmoron.appiumhandler.AppiumHandledDriver;

public class TestAppPage extends BasePage {

	public TestAppPage(AppiumHandledDriver driver) {
		super(driver);
	}
	
	/*** Define iOS Elements*/
	public static final String IOS_LABEL_ENTER_NAME_ID = "ios.label.enterName.id";
	public static final String IOS_TEXT_BOX_ENTER_NAME_ID = "ios.textBox.enterName.id";
	public static final String IOS_CLICK_BUTTON_ID = "ios.clickButton.id";
	public static final String IOS_CLICK_SWITCH_BUTTON_TEXT_ID = "ios.clickSwitchButtonText.id";
	public static final String IOS_SWITCH_BUTTON_ID = "ios.switchButton.id";
	public static final String IOS_HEY_THERE_TEXT_ID = "ios.heyThereText.id";
	public static final String IOS_SLIDER_BUTTON_ID = "ios.sliderButton.id";

	@Override
	public boolean isReady() {
		boolean status = this.isElementVisibleById(IOS_LABEL_ENTER_NAME_ID) && 
				this.isElementVisibleById(IOS_TEXT_BOX_ENTER_NAME_ID)  && 
				this.isElementVisibleById(IOS_CLICK_BUTTON_ID) &&
				this.isElementVisibleById(IOS_CLICK_SWITCH_BUTTON_TEXT_ID) &&
				this.isElementVisibleById(IOS_SWITCH_BUTTON_ID) &&
				this.isElementVisibleById(IOS_HEY_THERE_TEXT_ID) &&
				this.isElementVisibleById(IOS_SLIDER_BUTTON_ID);
		
		return  status;
	}

	/**
	 * Method to click on a switch button
	 */
	public void clickOnSwitchButton() {
			if(this.isElementVisibleById(IOS_SWITCH_BUTTON_ID)){
				this.getElementById(IOS_SWITCH_BUTTON_ID).click();
			}			
	}

}
