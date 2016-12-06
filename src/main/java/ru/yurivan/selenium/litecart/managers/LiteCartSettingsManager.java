package ru.yurivan.selenium.litecart.managers;

import java.util.ResourceBundle;

public class LiteCartSettingsManager {
    private final String BASE_URL;
    private final String ADMIN_PANEL_ADDRESS;
    private final String ADMIN_PANEL_LOGIN = "admin";
    private final String ADMIN_PANEL_PASSWORD = "admin";

    private final String TEST_DATA_FOLDER_PATH = "/testdata";
    private final String TEST_DATA_IMAGE_FOLDER_PATH = TEST_DATA_FOLDER_PATH + "/images";

    private static LiteCartSettingsManager instance;

    private LiteCartSettingsManager() {
        ResourceBundle rs = ResourceBundle.getBundle("common");
        BASE_URL = rs.getString("litecart.base_url");
        ADMIN_PANEL_ADDRESS = BASE_URL + "/admin";
    }

    public static LiteCartSettingsManager getInstance() {
        if (null == instance) {
            instance = new LiteCartSettingsManager();
        }
        return instance;
    }

    public String getBaseUrl() {
        return BASE_URL;
    }

    public String getAdminPanelAddress() {
        return ADMIN_PANEL_ADDRESS;
    }

    public String getAdminPanelLogin() {
        return ADMIN_PANEL_LOGIN;
    }

    public String getAdminPanelPassword() {
        return ADMIN_PANEL_PASSWORD;
    }

    public String getTestDataFolderPath() {
        return TEST_DATA_FOLDER_PATH;
    }

    public String getTestDataImageFolderPath() {
        return TEST_DATA_IMAGE_FOLDER_PATH;
    }
}
