package pro.mikey.mods.pop.data;

public enum Placement {
    TOP_LEFT,
    TOP_CENTER,
    TOP_RIGHT,
    MIDDLE_LEFT,
    MIDDLE_CENTER,
    MIDDLE_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_CENTER,
    BOTTOM_RIGHT;

    private static final Placement[] VALUES = values();

    public static Placement fromString(String string) {
        var upperInput = string.toUpperCase();
        for (var placement : VALUES) {
            if (placement.name().equals(upperInput)) {
                return placement;
            }
        }

        return TOP_LEFT;
    }

    public int[] location(int screenWidth, int screenHeight, int textWith) {
        return switch (this) {
            case TOP_CENTER -> new int[]{screenWidth / 2 - textWith / 2, 20};
            case TOP_RIGHT -> new int[]{screenWidth - textWith - 20, 20};
            case MIDDLE_LEFT -> new int[]{20, screenHeight / 2 - 10};
            case MIDDLE_CENTER -> new int[]{screenWidth / 2 - textWith / 2, screenHeight / 2 - 10};
            case MIDDLE_RIGHT -> new int[]{screenWidth - textWith - 20, screenHeight / 2 - 10};
            case BOTTOM_LEFT -> new int[]{20, screenHeight - 20};
            case BOTTOM_CENTER -> new int[]{screenWidth / 2 - textWith / 2, screenHeight - 85}; // More space for the hotbar, action bar, selected item text
            case BOTTOM_RIGHT -> new int[]{screenWidth - textWith - 20, screenHeight - 20};
            default -> new int[]{20, 20};
        };
    }
}
