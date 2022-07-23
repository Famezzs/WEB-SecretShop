package com.SecretShop.views;

import com.SecretShop.entities.ProductEntity;
import com.SecretShop.repositories.ProductRepository;
import com.SecretShop.repositories.PurchaseRepository;
import com.SecretShop.repositories.UserRepository;
import com.SecretShop.services.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import javax.validation.ValidationException;

@Route("products")
public class ProductView extends VerticalLayout {

    private UserRepository userRepository;
    private ProductRepository productRepository;
    private PurchaseRepository purchaseRepository;
    private TextField productName = new TextField("* Product Name");
    private TextField price = new TextField("* Price");
    private TextField userIdToSortBy = new TextField("User Id");
    private TextField productIdToRemove = new TextField("Id");
    private Grid<ProductEntity> productGrid = new Grid<>(ProductEntity.class);

    public ProductView(UserRepository userRepository,
                       ProductRepository productRepository,
                       PurchaseRepository purchaseRepository) {

        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;

        productGrid.setColumns("id", "productName", "price");
        add(getProductForm(), productGrid);

        refreshProductGrid();
    }

    private Component getProductForm() {

        var productLayout = new HorizontalLayout();

        productLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        productLayout.add(
                productName,
                price,
                getAddProductButton(),
                userIdToSortBy,
                getFindProductsByUserIdButton(),
                productIdToRemove,
                getRemoveProductButton());

        return productLayout;
    }

    private Button getAddProductButton() {

        var addProductButton = new Button("Add");
        addProductButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        addProductButton.addClickListener(click -> {
            try {

                var product = new ProductEntity(productName.getValue(), price.getValue());

                productRepository.save(product);

                refreshProductGrid();
            } catch (ValidationException exception) {

                add(getValidationFailureNotification
                        (exception.getMessage()));
            }
        });

        return addProductButton;
    }

    private Button getRemoveProductButton() {

        var removeProductButton = new Button("Remove");
        removeProductButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

        removeProductButton.addClickListener(click -> {
            try {

                var removeProductService = new RemoveProductService(
                        productIdToRemove.getValue(),
                        productRepository,
                        purchaseRepository
                );

                new AwaitTaskService().execute(new Thread(removeProductService));

                refreshProductGrid();
                clearFields();
            } catch (ValidationException exception) {
                add(getValidationFailureNotification(exception.getMessage()));
            }
        });

        return removeProductButton;
    }

    private Button getFindProductsByUserIdButton() {

        var findProductsByUserIdButton = new Button("Find by user id");
        findProductsByUserIdButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        findProductsByUserIdButton.addClickListener(click -> {

            try {

                if (userIdToSortBy.getValue().isEmpty() ||
                        userIdToSortBy.getValue().isBlank()) {

                    refreshProductGrid();
                } else {

                    var filteredProducts = new FindProductsByUserIdService(
                            userIdToSortBy.getValue(),
                            userRepository,
                            productRepository,
                            purchaseRepository).execute();

                    productGrid.setItems(filteredProducts);
                }
            } catch (ValidationException exception) {
                add(getValidationFailureNotification(exception.getMessage()));
            }
        });

        return findProductsByUserIdButton;
    }

    private Notification getValidationFailureNotification(String message) {

        var validationFailureNotification = Notification.show(message);
        validationFailureNotification.setThemeName("error");

        return validationFailureNotification;
    }

    private void refreshProductGrid() {
        productGrid.setItems(productRepository.findAll());
    }

    private void clearFields() {

        productName.clear();
        price.clear();
    }
}
