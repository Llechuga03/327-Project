package com.example.a327finalprojectcalorietracker;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.stream.Collectors;

public class foodItem {

    private float calories;
    private float carbs;
    private float protein;
    private float fat;
    @SerializedName("fdcId")
    private int fdcId;

    @SerializedName("availableDate")
    private String availableDate;

    @SerializedName("brandOwner")
    private String brandOwner;

    @SerializedName("dataSource")
    private String dataSource;

    @SerializedName("dataType")
    private String dataType;

    @SerializedName("description")
    private String description;

    @SerializedName("foodClass")
    private String foodClass;

    @SerializedName("ingredients")
    private String ingredients;

    @SerializedName("servingSize")
    private float servingSize;

    @SerializedName("servingSizeUnit")
    private String servingSizeUnit;

    @SerializedName("foodNutrients")
    private List<FoodNutrient> foodNutrients;

    @SerializedName("foodMeasures")
    private List<FoodMeasure> foodMeasures;

    // getters and setters for each field

    public int getFdcId() {
        return fdcId;
    }

    public void setFdcId(int fdcId) {
        this.fdcId = fdcId;
    }

    public String getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(String availableDate) {
        this.availableDate = availableDate;
    }

    public String getBrandOwner() {
        return brandOwner;
    }

    public void setBrandOwner(String brandOwner) {
        this.brandOwner = brandOwner;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDescription() {
        return description;
    } //GETS NAME

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFoodClass() {
        return foodClass     ;
    }

    public void setFoodClass(String foodClass) {
        this.foodClass = foodClass;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public float getServingSize() {
        return servingSize;
    }

    public void setServingSize(float servingSize) {
        this.servingSize = servingSize;
    }

    public String getServingSizeUnit() {
        return servingSizeUnit;
    }

    public void setServingSizeUnit(String servingSizeUnit) {
        this.servingSizeUnit = servingSizeUnit;
    }

    public List<FoodNutrient> getFoodNutrients() {
        return foodNutrients;
    }

    public void setFoodNutrients(List<FoodNutrient> foodNutrients) {
        this.foodNutrients = foodNutrients;
    }


    public String displayContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("foodItem {");
        sb.append("\nfdcId=").append(fdcId);
        sb.append(", \navailableDate='").append(availableDate).append('\'');
        sb.append(", \nbrandOwner='").append(brandOwner).append('\'');
        sb.append(", \ndataSource='").append(dataSource).append('\'');
        sb.append(", \ndataType='").append(dataType).append('\'');
        sb.append(", \ndescription='").append(description).append('\'');
        sb.append(", \nfoodClass='").append(foodClass).append('\'');
        sb.append(", \ningredients='").append(ingredients).append('\'');
        sb.append(", \nservingSize=").append(servingSize);
        sb.append(", \nservingSizeUnit='").append(servingSizeUnit).append('\'');
        if (foodNutrients != null) {
            sb.append(", \nfoodNutrients=[");
            for (FoodNutrient nutrient : foodNutrients) {
                sb.append("\n").append(nutrient.displayNutrients()).append(",");
            }
            // remove the last comma
            sb.setLength(sb.length() - 1);
            sb.append("\n]");
        } else {
            sb.append(", \nfoodNutrients=null");
        }
        sb.append("\n}");
        return sb.toString();
    }

    public void parseFoodNutrients() {
        for (FoodNutrient nutrient : foodNutrients) {
            String nutrientName = nutrient.getNutrientName();
            if ("Energy".equals(nutrientName)) { //KCAL
                this.calories = nutrient.getValue();
            } else if ("Carbohydrate, by difference".equals(nutrientName)) {//Grams
                this.carbs = nutrient.getValue();
            } else if ("Protein".equals(nutrientName)) {//Grams
                this.protein = nutrient.getValue();
            } else if ("Total lipid (fat)".equals(nutrientName)) {//Grams
                this.fat = nutrient.getValue();
            }
        }
    }

    public String displayContentSimple() {
        return "Serving Size: " + this.servingSize + this.servingSizeUnit + "\n" +
                "Calories: " + this.calories + "\n" +
                "Protein: " + this.protein + "\n" +
                "Carbs: " + this.carbs + "\n" +
                "Fat: " + this.fat;
    }

    // Getter method for calories
    public float getCalories() {
        return this.calories;
    }

    // Getter method for carbs
    public float getCarbs() {
        return this.carbs;
    }

    // Getter method for protein
    public float getProtein() {
        return this.protein;
    }

    // Getter method for fat
    public float getFat() {
        return this.fat;
    }

    public float getWeight(){return this.servingSize;}

    public void populateServingSize() {
        if (this.servingSize == 0 && this.foodMeasures != null && !this.foodMeasures.isEmpty()) {
            // Example logic: use the first available measure
            FoodMeasure measure = this.foodMeasures.get(0);
            this.servingSize = measure.getGramWeight();
            this.servingSizeUnit = "g"; // or any other logic to determine unit
        }
    }

    // Nested class for FoodMeasure
    public static class FoodMeasure {
        @SerializedName("disseminationText")
        private String disseminationText;

        @SerializedName("gramWeight")
        private float gramWeight;

        @SerializedName("id")
        private int id;

        @SerializedName("modifier")
        private String modifier;

        @SerializedName("rank")
        private int rank;

        @SerializedName("measureUnitAbbreviation")
        private String measureUnitAbbreviation;

        @SerializedName("measureUnitName")
        private String measureUnitName;

        @SerializedName("measureUnitId")
        private int measureUnitId;

        // Getters and Setters
        public String getDisseminationText() {
            return disseminationText;
        }

        public void setDisseminationText(String disseminationText) {
            this.disseminationText = disseminationText;
        }

        public float getGramWeight() {
            return gramWeight;
        }

        public void setGramWeight(float gramWeight) {
            this.gramWeight = gramWeight;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getModifier() {
            return modifier;
        }

        public void setModifier(String modifier) {
            this.modifier = modifier;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getMeasureUnitAbbreviation() {
            return measureUnitAbbreviation;
        }

        public void setMeasureUnitAbbreviation(String measureUnitAbbreviation) {
            this.measureUnitAbbreviation = measureUnitAbbreviation;
        }

        public String getMeasureUnitName() {
            return measureUnitName;
        }

        public void setMeasureUnitName(String measureUnitName) {
            this.measureUnitName = measureUnitName;
        }

        public int getMeasureUnitId() {
            return measureUnitId;
        }

        public void setMeasureUnitId(int measureUnitId) {
            this.measureUnitId = measureUnitId;
        }
    }

}
