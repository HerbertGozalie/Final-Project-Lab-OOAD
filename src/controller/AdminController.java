package controller;

import model.AdminModel;
import model.Item;

import java.util.List;

public class AdminController {

    public static List<Item> getPendingItems() {
        return AdminModel.getPendingItems();
    }

    public static String approveItem(int itemId) {
        if (itemId <= 0) {
            return "Invalid item ID.";
        }

        boolean isApproved = AdminModel.approveItem(itemId);
        return isApproved ? "Item approved successfully!" : "Failed to approve item.";
    }

    public static String declineItem(int itemId, String reason) {
        if (itemId <= 0) {
            return "Invalid item ID.";
        }

        if (reason == null || reason.trim().isEmpty()) {
            return "Reason for declining cannot be empty.";
        }

        boolean isDeclined = AdminModel.declineItem(itemId, reason);
        return isDeclined ? "Item declined successfully!" : "Failed to decline item.";
    }
}
