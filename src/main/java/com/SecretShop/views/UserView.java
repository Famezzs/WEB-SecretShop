package com.SecretShop.views;

import com.SecretShop.repositories.ProductRepository;
import com.SecretShop.repositories.PurchaseRepository;
import com.SecretShop.repositories.UserRepository;
import com.SecretShop.entities.UserEntity;
import com.SecretShop.services.AwaitTaskService;
import com.SecretShop.services.FindUsersByProductIdService;
import com.SecretShop.services.RemoveUserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import javax.validation.ValidationException;

@Route("users")
public class UserView extends VerticalLayout {
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private PurchaseRepository purchaseRepository;
    private TextField firstName = new TextField("* First name");
    private TextField lastName = new TextField("* Last name");
    private EmailField email = new EmailField("* Email");
    private TextField balance = new TextField("* Balance");
    private TextField productIdToSortBy = new TextField("Product Id");
    private TextField userIdToDelete = new TextField("Id");
    private Grid<UserEntity> userGrid = new Grid<>(UserEntity.class);

    public UserView(UserRepository userRepository,
                    ProductRepository productRepository,
                    PurchaseRepository purchaseRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;

        userGrid.setColumns("id", "email", "firstName", "lastName", "balance");
        add(getUserForm(), userGrid);

        refreshUserGrid();
    }

    private Component getUserForm() {

        var userLayout = new HorizontalLayout();

        userLayout.setAlignItems(Alignment.BASELINE);
        userLayout.add(
                firstName,
                lastName,
                email,
                balance,
                getAddUserButton(),
                productIdToSortBy,
                getFindUsersByProductIdButton(),
                userIdToDelete,
                getRemoveUserButton());

        return userLayout;
    }

    private Button getAddUserButton() {

        var addUserButton = new Button("Add");
        addUserButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        addUserButton.addClickListener(click -> {
            try {

                userRepository.save(
                        new UserEntity(firstName.getValue(),
                                lastName.getValue(),
                                email.getValue(),
                                balance.getValue())
                );

                refreshUserGrid();
            } catch (ValidationException exception) {
                add(getValidationFailureNotification(exception.getMessage()));
            }
        });

        return addUserButton;
    }

    private Button getRemoveUserButton() {

        var removeUserButton = new Button("Remove");
        removeUserButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

        removeUserButton.addClickListener(click -> {
           try {

               var removeUserService = new RemoveUserService(
                       userIdToDelete.getValue(),
                       userRepository,
                       purchaseRepository
                       );

               new AwaitTaskService().execute(new Thread(removeUserService));

               refreshUserGrid();
               clearFields();
           } catch (ValidationException exception) {
               add(getValidationFailureNotification(exception.getMessage()));
           }
        });

        return removeUserButton;
    }

    private Button getFindUsersByProductIdButton() {

        var findUsersByProductIdButton = new Button("Find by product id");
        findUsersByProductIdButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        findUsersByProductIdButton.addClickListener(click -> {

            try {

                if (productIdToSortBy.getValue().isEmpty() ||
                        productIdToSortBy.getValue().isBlank()) {

                    refreshUserGrid();
                } else {

                    var filteredUsers = new FindUsersByProductIdService(
                            productIdToSortBy.getValue(),
                            userRepository,
                            productRepository,
                            purchaseRepository).execute();

                    userGrid.setItems(filteredUsers);
                }
            } catch (ValidationException exception) {
                add(getValidationFailureNotification(exception.getMessage()));
            }
        });

        return findUsersByProductIdButton;
    }

    private Notification getValidationFailureNotification(String message) {

        var validationFailureNotification = Notification.show(message);
        validationFailureNotification.setThemeName("error");

        return validationFailureNotification;
    }

    private void refreshUserGrid() {
        userGrid.setItems(userRepository.findAll());
    }

    private void clearFields() {

        firstName.clear();
        lastName.clear();
        email.clear();
        balance.clear();
        productIdToSortBy.clear();
        userIdToDelete.clear();
    }
}
