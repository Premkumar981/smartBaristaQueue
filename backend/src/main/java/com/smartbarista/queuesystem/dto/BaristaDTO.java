package com.smartbarista.queuesystem.dto;

import com.smartbarista.queuesystem.model.DrinkType;

public class BaristaDTO {
    private Long id;
    private String name;
    private Double currentLoad;
    private Integer totalWorkTime;
    private boolean available;
    private DrinkType currentDrink;
    private Integer remainingPrepSeconds;
    private Integer totalDrinksPrepared;

    public BaristaDTO() {
    }

    public BaristaDTO(Long id, String name, Double currentLoad, Integer totalWorkTime, boolean available,
            DrinkType currentDrink, Integer remainingPrepSeconds, Integer totalDrinksPrepared) {
        this.id = id;
        this.name = name;
        this.currentLoad = currentLoad;
        this.totalWorkTime = totalWorkTime;
        this.available = available;
        this.currentDrink = currentDrink;
        this.remainingPrepSeconds = remainingPrepSeconds;
        this.totalDrinksPrepared = totalDrinksPrepared;
    }

    public static BaristaDTOBuilder builder() {
        return new BaristaDTOBuilder();
    }

    public static class BaristaDTOBuilder {
        private Long id;
        private String name;
        private Double currentLoad;
        private Integer totalWorkTime;
        private boolean available;
        private DrinkType currentDrink;
        private Integer remainingPrepSeconds;
        private Integer totalDrinksPrepared;

        public BaristaDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BaristaDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public BaristaDTOBuilder currentLoad(Double currentLoad) {
            this.currentLoad = currentLoad;
            return this;
        }

        public BaristaDTOBuilder totalWorkTime(Integer totalWorkTime) {
            this.totalWorkTime = totalWorkTime;
            return this;
        }

        public BaristaDTOBuilder available(boolean available) {
            this.available = available;
            return this;
        }

        public BaristaDTOBuilder currentDrink(DrinkType currentDrink) {
            this.currentDrink = currentDrink;
            return this;
        }

        public BaristaDTOBuilder remainingPrepSeconds(Integer remainingPrepSeconds) {
            this.remainingPrepSeconds = remainingPrepSeconds;
            return this;
        }

        public BaristaDTOBuilder totalDrinksPrepared(Integer totalDrinksPrepared) {
            this.totalDrinksPrepared = totalDrinksPrepared;
            return this;
        }

        public BaristaDTO build() {
            return new BaristaDTO(id, name, currentLoad, totalWorkTime, available, currentDrink, remainingPrepSeconds,
                    totalDrinksPrepared);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCurrentLoad() {
        return currentLoad;
    }

    public void setCurrentLoad(Double currentLoad) {
        this.currentLoad = currentLoad;
    }

    public Integer getTotalWorkTime() {
        return totalWorkTime;
    }

    public void setTotalWorkTime(Integer totalWorkTime) {
        this.totalWorkTime = totalWorkTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public DrinkType getCurrentDrink() {
        return currentDrink;
    }

    public void setCurrentDrink(DrinkType currentDrink) {
        this.currentDrink = currentDrink;
    }

    public Integer getRemainingPrepSeconds() {
        return remainingPrepSeconds;
    }

    public void setRemainingPrepSeconds(Integer remainingPrepSeconds) {
        this.remainingPrepSeconds = remainingPrepSeconds;
    }

    public Integer getTotalDrinksPrepared() {
        return totalDrinksPrepared;
    }

    public void setTotalDrinksPrepared(Integer totalDrinksPrepared) {
        this.totalDrinksPrepared = totalDrinksPrepared;
    }
}
