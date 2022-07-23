package com.SecretShop.views;

import com.SecretShop.entities.ProductEntity;
import com.SecretShop.entities.PurchaseEntity;
import com.SecretShop.entities.UserEntity;
import com.SecretShop.repositories.ProductRepository;
import com.SecretShop.repositories.PurchaseRepository;
import com.SecretShop.repositories.UserRepository;
import com.SecretShop.services.PurchaseService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import javax.validation.ValidationException;

@Route("purchases")
public class PurchaseView extends VerticalLayout {
    private TextField userId = new TextField("* User ID");
    private TextField productId = new TextField("* Product ID");
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private PurchaseRepository purchaseRepository;
    private Grid<UserEntity> userGrid = new Grid<>(UserEntity.class);
    private Grid<ProductEntity> productGrid = new Grid<>(ProductEntity.class);
    private Grid<PurchaseEntity> purchaseGrid = new Grid<>(PurchaseEntity.class);

    public PurchaseView(UserRepository userRepository,
                        ProductRepository productRepository,
                        PurchaseRepository purchaseRepository) {

        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;

        userGrid.setColumns("id", "email", "firstName", "lastName", "balance");
        productGrid.setColumns("id", "productName", "price");
        purchaseGrid.setColumns("id", "userId", "productId");

        add(
                getPurchaseForm(),
                getPurchaseTextPrompt(),
                purchaseGrid,
                getUserTextPrompt(),
                userGrid,
                getProductTextPrompt(),
                productGrid
        );

        refreshGrids();
    }

    private Component getPurchaseForm() {

        var purchaseLayout = new HorizontalLayout();

        purchaseLayout.setAlignItems(Alignment.BASELINE);
        purchaseLayout.add(userId, productId, getPurchaseButton());

        return purchaseLayout;
    }

    private Component getPurchaseTextPrompt() {
        return new Text("Purchases");
    }

    private Component getUserTextPrompt() {
        return new Text("Users");
    }

    private Component getProductTextPrompt() {
        return new Text("Products");
    }

    private Notification getValidationFailureNotification(String message) {

        var validationFailureNotification = Notification.show(message);
        validationFailureNotification.setThemeName("error");

        return validationFailureNotification;
    }

    private Notification getPurchaseSuccessNotification() {

        var purchaseSuccessNotification = Notification.show("Success!");
        purchaseSuccessNotification.setThemeName("success");

        return purchaseSuccessNotification;
    }

    private Button getPurchaseButton() {

        var purchaseButton = new Button("Purchase");
        purchaseButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        purchaseButton.addClickListener(click -> {
            try {
                new PurchaseService().execute(
                        userId.getValue(),
                        productId.getValue(),
                        userRepository,
                        productRepository,
                        purchaseRepository
                );

                add(getPurchaseSuccessNotification());

                refreshGrids();
                clearFields();
            } catch (ValidationException exception) {
                add(getValidationFailureNotification
                        (exception.getMessage()));
            }
        });

        return purchaseButton;
    }

    private void refreshGrids() {

        userGrid.setItems(userRepository.findAll());
        productGrid.setItems(productRepository.findAll());
        purchaseGrid.setItems(purchaseRepository.findAll());
    }

    private void clearFields() {

        userId.clear();
        productId.clear();
    }
}
