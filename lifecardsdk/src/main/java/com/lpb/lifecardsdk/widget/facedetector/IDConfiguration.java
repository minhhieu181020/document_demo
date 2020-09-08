package com.lpb.lifecardsdk.widget.facedetector;

public class IDConfiguration {
    private CardType cardType;
    private boolean isEnableSound;
    private SDKConfiguration.CardSide cardSide;
    private boolean isReadBothSide;
    private boolean isEnableSanityCheck;
    private boolean isEnableTiltChecking;

    IDConfiguration(IDConfiguration.Builder builder) {
        this.isEnableSound = builder.isEnableSound;
        this.cardType = builder.cardType;
        this.cardSide = builder.cardSide;
        this.isReadBothSide = builder.isReadBothSide;
        this.isEnableSanityCheck = builder.isEnableSanityCheck;
        this.isEnableTiltChecking = builder.isEnableTiltChecking;
    }

    public CardType getCardType() {
        return this.cardType;
    }

    public boolean getEnableSound() {
        return this.isEnableSound;
    }

    public SDKConfiguration.CardSide getCardSide() {
        return this.cardSide;
    }

    public boolean isReadBothSide() {
        return this.isReadBothSide;
    }

    public boolean isEnableSanityCheck() {
        return this.isEnableSanityCheck;
    }

    public boolean isEnableTiltChecking() {
        return this.isEnableTiltChecking;
    }

    public static class Builder {
        private CardType cardType;
        private boolean isEnableSound = false;
        private SDKConfiguration.CardSide cardSide;
        private boolean isReadBothSide = false;
        private boolean isEnableSanityCheck = false;
        private boolean isEnableTiltChecking;

        public Builder() {
            this.cardSide = SDKConfiguration.CardSide.FRONT;
            this.isEnableTiltChecking = false;
        }

        public IDConfiguration.Builder setEnableSound(boolean isEnable) {
            this.isEnableSound = isEnable;
            return this;
        }

        public IDConfiguration.Builder setCardType(CardType cardType) {
            this.cardType = cardType;
            return this;
        }

        public IDConfiguration.Builder setCardSide(SDKConfiguration.CardSide cardSide) {
            this.cardSide = cardSide;
            return this;
        }

        public IDConfiguration.Builder setReadBothSide(boolean readBothSide) {
            this.isReadBothSide = readBothSide;
            return this;
        }

        public IDConfiguration.Builder setEnableSanityCheck(boolean isEnableSanityCheck) {
            this.isEnableSanityCheck = isEnableSanityCheck;
            return this;
        }

        public IDConfiguration.Builder setEnableTiltChecking(boolean isEnableTiltChecking) {
            this.isEnableTiltChecking = isEnableTiltChecking;
            return this;
        }

        public IDConfiguration build() {
            return new IDConfiguration(this);
        }
    }
}