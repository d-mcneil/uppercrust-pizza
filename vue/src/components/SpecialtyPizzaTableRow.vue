<template>
  <div class="table-row">
    <div class="table-cell-pizza-name">
      <input v-model="name" placeholder="New Specialty Pizza" />
    </div>
    <div class="table-cell-pizza-price">
      <input
        v-model="price"
        type="text"
        @focus="formatAsNumber"
        @blur="formatAsMoney"
        placeholder="Price"
      />
    </div>
    <div class="table-cell-pizza-size">
      <select v-model="sizeId" :class="{ 'no-option-selected': sizeId === -1 }">
        <option
          class="placeholder"
          v-if="sizeId === -1"
          value="-1"
          selected
          disabled
        >
          Size
        </option>
        <!-- TODO: allow PUT request to edit size and then remove the disabled binding below -->
        <option
          :disabled="false"
          v-for="size of $store.state.sizes"
          :key="size.id"
          :value="size.id"
        >
          {{ size.name }}
        </option>
      </select>
    </div>
    <div class="table-cell-pizza-crust">
      <select
        v-model="crustId"
        :class="{ 'no-option-selected': crustId === -1 }"
      >
        <option
          class="placeholder"
          v-if="crustId === -1"
          value="-1"
          selected
          disabled
        >
          Crust
        </option>
        <!-- TODO: allow PUT request to edit crust and then remove the disabled binding below -->
        <option
          :disabled="false"
          v-for="crust of $store.state.crusts"
          :key="crust.id"
          :value="crust.id"
        >
          {{ crust.name }}
        </option>
      </select>
    </div>
    <div class="table-cell-pizza-toppings">
      <select multiple size="5" v-model="toppingIds">
        <!-- TODO: allow PUT request to edit toppings and then remove the disabled binding below -->
        <option
          :disabled="false"
          v-for="topping of $store.state.toppings"
          :key="topping.id"
          :value="topping.id"
        >
          {{ topping.name }}
        </option>
      </select>
    </div>
    <div class="table-cell-pizza-sauce">
      <select
        v-model="sauceId"
        :class="{ 'no-option-selected': sauceId === -1 }"
      >
        <option
          class="placeholder"
          v-if="sauceId === -1"
          value="-1"
          selected
          disabled
        >
          Sauce
        </option>
        <!-- TODO: allow PUT request to edit sauce and then remove the disabled binding below -->
        <option
          :disabled="false"
          v-for="sauce of $store.state.sauces"
          :key="sauce.id"
          :value="sauce.id"
        >
          {{ sauce.name }}
        </option>
      </select>
    </div>
    <div class="table-cell-pizza-available">
      <select v-model="isAvailable">
        <option value="true">Available</option>
        <option value="false">Not Available</option>
      </select>
    </div>
    <div v-if="!pizza" class="table-cell-buttons table-icon-wrapper">
      <i
        class="fa-floppy-disk fa-solid grow employee-button-icon"
        @click="addNewSpecialtyPizza"
      ></i>
    </div>
    <div
      v-else-if="areUnsavedChanges"
      class="table-cell-buttons table-button-wrapper"
    >
      <small-button
        buttonText="Save Changes"
        :clickHandler="saveChanges"
      ></small-button>
      <small-button
        buttonText="Discard Changes"
        :clickHandler="discardChanges"
      ></small-button>
    </div>
    <div v-else class="table-cell-buttons table-icon-wrapper">
      <i
        class="fa-trash fa-solid grow employee-button-icon row-delete-button icon-hide"
      ></i>
    </div>
    <div class="table-cell-pizza-description">
      <textarea
        placeholder="Pizza Description"
        v-model="description"
      ></textarea>
    </div>
    <div class="table-cell-pizza-url">
      <textarea placeholder="Picture Url" v-model="imageURL"></textarea>
    </div>
  </div>
</template>

<script>
import SmallButton from "./SmallButton.vue";
import specialtyPizzaService from "../services/SpecialtyPizzaService.js";

export default {
  name: "specialty-pizza-table-row",
  components: { SmallButton },
  props: ["pizza"],
  data() {
    return {
      pizzaId: -1,
      name: "",
      price: "",
      sizeId: -1,
      crustId: -1,
      toppingIds: [],
      sauceId: -1,
      isAvailable: true,
      imageURL: "",
      description: "",
      currentPizza: {},
    };
  },
  computed: {
    areUnsavedChanges() {
      if (!this.pizza) {
        return false;
      }
      const {
        name,
        price,
        size,
        crust,
        toppings,
        sauce,
        isAvailable,
        imageURL,
        description,
      } = this.currentPizza;

      // Check that each topping in the original topping list accounted for in the local topping list
      let areToppingsDifferent = false;
      toppings.forEach((topping) => {
        if (this.toppingIds.indexOf(topping) === -1) {
          areToppingsDifferent = true;
        }
      });

      return (
        this.name !== name ||
        this.price !== `$${price.toFixed(2)}` ||
        this.sizeId !== size.id ||
        this.crustId !== crust.id ||
        this.sauceId !== sauce.id ||
        this.isAvailable !== isAvailable ||
        this.description !== description ||
        this.imageURL !== imageURL ||
        this.toppingIds.length !== toppings.length ||
        areToppingsDifferent
      );
    },
  },
  created() {
    if (this.pizza) {
      this.currentPizza = this.pizza;
      this.initializeRow();
    }
  },
  methods: {
    resetRow() {
      this.pizzaId = -1;
      this.name = "";
      this.price = "";
      this.sizeId = -1;
      this.crustId = -1;
      this.toppingIds = [];
      this.sauceId = -1;
      this.isAvailable = true;
      this.imageURL = "";
      this.description = "";
    },
    initializeRow() {
      const {
        id,
        name,
        price,
        size,
        crust,
        toppings,
        sauce,
        isAvailable,
        imageURL,
        description,
      } = this.currentPizza;
      this.pizzaId = id;
      this.name = name;
      this.price = `$${price.toFixed(2)}`;
      this.sizeId = size.id;
      this.crustId = crust.id;
      this.toppingIds = toppings;
      this.sauceId = sauce.id;
      this.isAvailable = isAvailable;
      this.imageURL = imageURL;
      this.description = description;
    },
    formatAsMoney(event) {
      const price = event.target.value
        ? event.target.value >= 1000
          ? "999.99"
          : event.target.value
        : "0";
      event.target.setAttribute("type", "text");
      event.target.value = `$${Number.parseFloat(price).toFixed(2)}`;
      this.price = event.target.value;
    },
    formatAsNumber(event) {
      event.target.value = event.target.value.replace("$", "");
      event.target.setAttribute("type", "number");
    },
    discardChanges() {
      if (
        confirm(
          `Are you sure you want to discard the changes to ${this.currentPizza.name}`
        )
      ) {
        this.initializeRow();
      }
    },
    saveChanges() {
      const { areInputsValid, buildSpecialtyPizza, pizzaId } = this;
      let updatedSpecialtyPizza;

      if (areInputsValid()) {
        updatedSpecialtyPizza = buildSpecialtyPizza(pizzaId);
        specialtyPizzaService
          .updateSpecialtyPizza(updatedSpecialtyPizza)
          .then((res) => {
            if (res.status === 200) {
              this.currentPizza = {
                crust: updatedSpecialtyPizza.pizza.crust,
                description: updatedSpecialtyPizza.description,
                id: updatedSpecialtyPizza.itemId,
                imageURL: updatedSpecialtyPizza.imageURL,
                isAvailable: updatedSpecialtyPizza.available,
                name: updatedSpecialtyPizza.name,
                price: Number.parseFloat(updatedSpecialtyPizza.price),
                sauce: updatedSpecialtyPizza.pizza.sauce,
                size: updatedSpecialtyPizza.pizza.size,
                toppings: updatedSpecialtyPizza.pizza.toppings.map(
                  (topping) => topping.id
                ),
              };
            }
          })
          .catch(() =>
            alert(
              `There was an error updating the ${this.currentPizza.name}. Please try again later.`
            )
          );
      }
    },
    buildSpecialtyPizza(id) {
      // ***** BUILD THE SPECIALTY PIZZA THAT WILL BE USED IN THE REQUEST IN CORRECT FORMAT *****
      const {
        name,
        price,
        sizeId,
        crustId,
        toppingIds,
        sauceId,
        isAvailable,
        imageURL,
        description,
      } = this;
      const specialtyPizza = {
        name,
        price: price.replace("$", ""),
        imageURL,
        description,
      };
      if (id) specialtyPizza.itemId = id;
      const pizza = {};
      pizza.size = { id: sizeId };
      pizza.crust = { id: crustId };
      pizza.sauce = { id: sauceId };
      pizza.toppings = toppingIds.map((id) => ({ id }));
      specialtyPizza.available = isAvailable;
      specialtyPizza.pizza = pizza;
      return specialtyPizza;
    },
    addNewSpecialtyPizza() {
      const {
        areInputsValid,
        name,
        price,
        toppingIds,
        isAvailable,
        resetRow,
        imageURL,
        description,
        buildSpecialtyPizza,
      } = this;
      if (areInputsValid()) {
        const specialtyPizza = buildSpecialtyPizza();
        specialtyPizzaService
          .createSpecialtyPizza(specialtyPizza)
          .then((res) => {
            if (res.status === 200) {
              // TODO: change status to 201
              this.$store.commit("ADD_SPECIALTY_PIZZA", {
                id: res.data,
                name,
                price: Number.parseFloat(price.replace("$", "")),
                isAvailable,
                size: specialtyPizza.pizza.size,
                crust: specialtyPizza.pizza.crust,
                sauce: specialtyPizza.pizza.sauce,
                toppings: toppingIds,
                imageURL,
                description,
              });
              resetRow();
            }
          })
          .catch(() =>
            // error
            {
              alert(
                "There was an error saving the new specialty pizza. Please try again later."
              );
            }
          );
      }
    },
    areInputsValid() {
      const {
        name,
        price,
        sizeId,
        crustId,
        toppingIds,
        sauceId,
        imageURL,
        description,
      } = this;
      if (!name) {
        alert("New specialty pizza must have a name.");
        return false;
      }
      if (name.length > 40) {
        alert(
          "New specialty pizza must have a name that is 40 characters or less."
        );
        return false;
      }
      if (Number.parseFloat(price.replace("$", "")) < 0.01) {
        alert(
          "New specialty pizza must have a price that is greater than $0.00."
        );
        return false;
      }
      if (sizeId <= 0) {
        alert("New specialty pizza must have a size selected");
        return false;
      }
      if (crustId <= 0) {
        alert("New specialty pizza must have a crust selected");
        return false;
      }
      if (sauceId <= 0) {
        alert("New specialty pizza must have a sauce selected");
        return false;
      }
      if (!toppingIds.length) {
        alert("New specialty pizza must have at least 1 topping selected");
        return false;
      }
      if (!description) {
        alert("New specialty pizza must have a description.");
        return false;
      }
      if (!imageURL) {
        alert("New specialty pizza must have an image URL.");
        return false;
      }
      return true;
    },
  },
};
</script>

<style>
div.table-row {
  display: grid;
  grid-template-columns: 2fr 0.75fr 0.75fr 1fr 1.25fr 1fr 1fr 6rem;
  grid-template-areas: "name price size crust toppings sauce available buttons";
  grid-template-rows: auto auto;
  gap: 5px;
}

div.table-row:not(#header-row) {
  grid-template-areas:
    "name price size crust toppings sauce available buttons"
    "description description description description toppings url url buttons";
}

div.table-row#header-row {
  text-align: center;
}

div.table-row:not(#header-row):not(:nth-child(2)) {
  margin-top: 40px;
}

div.table-row:nth-child(2) {
  margin-top: 5px;
}

div.table-row > div {
  width: 100%;
}

div.table-row textarea,
div.table-row input,
div.table-row select {
  width: 100%;
  height: 100%;
  padding: 3px;
}

div.table-row textarea {
  resize: none;
  padding: 5px;
}

div.table-cell-pizza-name {
  grid-area: name;
}
div.table-cell-pizza-price {
  grid-area: price;
}
div.table-cell-pizza-size {
  grid-area: size;
}
div.table-cell-pizza-crust {
  grid-area: crust;
}
div.table-cell-pizza-toppings {
  grid-area: toppings;
}
div.table-cell-pizza-available {
  grid-area: available;
}
div.table-cell-buttons {
  grid-area: buttons;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
}
div.table-row:not(#header-row) {
  height: 6.5rem;
}
div.table-cell-pizza-description {
  grid-area: description;
}
div.table-cell-pizza-url {
  grid-area: url;
}

select.no-option-selected {
  color: rgb(170, 170, 170);
}

/************************* PRICE CELL STYLE *************************/
/***** https://www.w3schools.com/howto/howto_css_hide_arrow_number.asp *****/
/* hide arrows in the number input field */
input[type="number"]::-webkit-inner-spin-button,
input[type="number"]::-webkit-outer-spin-button {
  appearance: none;
  margin: 0;
}
/* hide the arrows in the number input field for firefox */
input[type="number"] {
  -moz-appearance: textfield;
}

/************************* FINAL CELL STYLE *************************/
div.table-button-wrapper button:first-child {
  margin-bottom: 5px;
}
div.table-button-wrapper button:last-child {
  background-color: var(--primary-color);
  border-color: var(--primary-color);
}
div.table-icon-wrapper {
  color: var(--primary-color);
  padding-left: 20px;
}
div.table-icon-wrapper i {
  padding: 8px;
  font-size: 1.25rem;
}
div:last-child i.row-delete-button {
  background-color: var(--primary-color);
}
</style>
