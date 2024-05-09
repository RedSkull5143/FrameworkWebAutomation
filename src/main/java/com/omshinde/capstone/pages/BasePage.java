package com.omshinde.capstone.pages;

import com.omshinde.capstone.actions.ButtonAction;
import com.omshinde.capstone.actions.TextBox;
import com.omshinde.capstone.actions.WebActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {
    protected WebDriver webDriver;
    protected ButtonAction buttonActions;
    protected TextBox textBox;
    protected WebActions webActions;

    public BasePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver,this);
        this.buttonActions=new ButtonAction(webDriver);
        this.textBox=new TextBox(webDriver);
        this.webActions=new WebActions(webDriver);
    }

}
