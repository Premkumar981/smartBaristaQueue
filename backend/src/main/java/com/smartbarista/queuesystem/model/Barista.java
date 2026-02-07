package com.smartbarista.queuesystem.model;

import jakarta.persistence.*;

@Entity
public class Barista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double currentLoad; // actual work / average work

    private Integer totalWorkTime; // total minutes worked
    private Integer totalDrinksPrepared;
    private boolean available;

    public Barista() {
    }

    public Barista(Long id, String name, Double currentLoad, Integer totalWorkTime, Integer totalDrinksPrepared,
            boolean available) {
        this.id = id;
        this.name = name;
        this.currentLoad = currentLoad;
        this.totalWorkTime = totalWorkTime;
        this.totalDrinksPrepared = totalDrinksPrepared;
        this.available = available;
    }

    public static BaristaBuilder builder() {
        return new BaristaBuilder();
    }

    public static class BaristaBuilder {
        private Long id;
        private String name;
        private Double currentLoad;
        private Integer totalWorkTime;
        private Integer totalDrinksPrepared;
        private boolean available;

        public BaristaBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BaristaBuilder name(String name) {
            this.name = name;
            return this;
        }

        public BaristaBuilder currentLoad(Double currentLoad) {
            this.currentLoad = currentLoad;
            return this;
        }

        public BaristaBuilder totalWorkTime(Integer totalWorkTime) {
            this.totalWorkTime = totalWorkTime;
            return this;
        }

        public BaristaBuilder totalDrinksPrepared(Integer totalDrinksPrepared) {
            this.totalDrinksPrepared = totalDrinksPrepared;
            return this;
        }

        public BaristaBuilder available(boolean available) {
            this.available = available;
            return this;
        }

        public Barista build() {
            return new Barista(id, name, currentLoad, totalWorkTime, totalDrinksPrepared, available);
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

    public Integer getTotalDrinksPrepared() {
        return totalDrinksPrepared != null ? totalDrinksPrepared : 0;
    }

    public void setTotalDrinksPrepared(Integer totalDrinksPrepared) {
        this.totalDrinksPrepared = totalDrinksPrepared;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
