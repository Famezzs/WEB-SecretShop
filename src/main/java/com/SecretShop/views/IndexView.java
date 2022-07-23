package com.SecretShop.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class IndexView extends VerticalLayout {

    public IndexView() {
        setAlignItems(Alignment.CENTER);

        add(
                getGreeting(),
                getTextPrompt(),
                centerHorizontally(
                        getViewUsersButton(),
                        getViewProductsButton(),
                        getViewPurchasesButton())
        );
    }

    private Component getGreeting() {

        var greeting = new Html("<h1>Welcome to the Secret Shop!</h1>");
        greeting.getElement().getStyle().set("font-size", "24px");

        return greeting;
    }

    private Component getTextPrompt() {
        return new Text("What would you like to do?");
    }

    private Component getViewUsersButton() {

        var viewUsersButton = new Button("View Users");
        viewUsersButton.addThemeVariants(ButtonVariant.LUMO_LARGE);

        viewUsersButton.addClickListener(click -> {
            UI.getCurrent().getPage().setLocation("http://localhost:8080/users");
        });

        return viewUsersButton;
    }

    private Component getViewProductsButton() {

        var viewProductsButton = new Button("View Products");
        viewProductsButton.addThemeVariants(ButtonVariant.LUMO_LARGE);

        viewProductsButton.addClickListener(click -> {
            UI.getCurrent().getPage().setLocation("http://localhost:8080/products");
        });

        return viewProductsButton;
    }

    private Component getViewPurchasesButton() {

        var viewPurchasesButton = new Button("View Purchases");
        viewPurchasesButton.addThemeVariants(ButtonVariant.LUMO_LARGE);

        viewPurchasesButton.addClickListener(click -> {
            UI.getCurrent().getPage().setLocation("http://localhost:8080/purchases");
        });

        return viewPurchasesButton;
    }
    
    private Component centerHorizontally(Component... components) {

        var horizontalLayout = new HorizontalLayout();
        horizontalLayout.setAlignItems(Alignment.CENTER);

        horizontalLayout.add(components);

        return horizontalLayout;
    }
}
