<template>
  <section id="order-confirmation-section" class="customer-section">
    <horizontal-hero></horizontal-hero>
    <div id="order-confirmation">
      <h1>Order Confirmation</h1>
      <p>
        Thank you, {{ confirmedOrder.name }}! This is a fictitious pizza shop,
        so no payment was processed, and pizzas will not be prepared. However, a
        sample confirmation email was sent to the email address provided.
      </p>

      <h3>Order Summary</h3>
      <div id="order-summary-customer">
        <p
          id="delivery-info-order-confirmation"
          v-if="confirmedOrder.isDelivery"
        >
          Delivery Address: <span>{{ confirmedOrder.address }}</span>
        </p>

        <template v-if="confirmedOrder.menuItems.length > 0">
          <p
            v-for="menuItem of confirmedOrder.menuItems"
            :key="menuItem.itemId"
          >
            {{
              $store.state.specialtyPizzas.find(
                (storeItem) => storeItem.id === menuItem.itemId
              ).name
            }}
            <span>X {{ menuItem.quantity }}</span>
          </p>
        </template>

        <p v-if="confirmedOrder.customPizzas.length > 0">
          Custom Pizzas <span>X {{ totalPizzaQuantity }}</span>
        </p>
      </div>

      <checkout-amount
        :omitButton="true"
        :givenTotal="`${(confirmedOrder.tax + confirmedOrder.subtotal).toFixed(
          2
        )}`"
        :givenTax="`${confirmedOrder.tax.toFixed(2)}`"
        :givenSubtotal="`${confirmedOrder.subtotal.toFixed(2)}`"
      />
    </div>
  </section>
</template>

<script>
import HorizontalHero from "../components/HorizontalHero.vue";
import CheckoutAmount from "../components/CheckoutAmount.vue";
import menuService from "../services/MenuService.js";

export default {
  components: { HorizontalHero, CheckoutAmount },
  data() {
    return {
      confirmedOrder: {},
    };
  },
  name: "order-confirmation",

  beforeCreate() {
    menuService
      .getMenuItems()
      .catch(() =>
        alert(
          "There was an error retrieving the menu items. Please try again later."
        )
      ); // TODO: .catch alert if error

    if (!Object.keys(this.$store.state.cart).length) {
      this.$router.push({ name: "home" });
    }
  },
  created() {
    this.$store.commit("CLEAR_CART");
    this.confirmedOrder = this.$store.state.confirmedOrder;

    let string = this.confirmedOrder.address.split("|||");
    this.confirmedOrder.address = "";
    for (let i = 0; i < string.length; i++) {
      if (i == 0) {
        this.confirmedOrder.address = this.confirmedOrder.address + string[i];
      } else if (i == string.length - 1) {
        this.confirmedOrder.address =
          this.confirmedOrder.address + "\t" + string[i];
      } else {
        this.confirmedOrder.address =
          this.confirmedOrder.address + ", " + string[i];
      }
    }
  },
  destroyed() {
    this.$store.commit("CLEAR_CONFIRMED_ORDER");
  },

  computed: {
    totalMenuItemQuantity() {
      let menuItemQuantity = 0;

      if (this.confirmedOrder.menuItems.length > 0) {
        menuItemQuantity = this.confirmedOrder.menuItems.reduce(
          (sum, menuItem) => sum + menuItem.quantity,
          0
        );
      }

      return menuItemQuantity;
    },

    totalPizzaQuantity() {
      let pizzaQuantity = 0;
      if (this.confirmedOrder.customPizzas.length > 0) {
        pizzaQuantity = this.confirmedOrder.customPizzas.reduce(
          (sum, pizza) => sum + pizza.quantity,
          0
        );
      }
      return pizzaQuantity;
    },
  },
};
</script>

<style scoped>
#order-summary-customer p {
  justify-self: end;
  font-weight: bold;

  margin: 0.5rem;
  margin-left: 1rem;
}

#order-summary-customer p:first-child {
  margin-top: 2rem;
}

#order-summary-customer p#delivery-info-order-confirmation {
  margin-bottom: 2rem;
}

#order-summary-customer p span {
  font-weight: normal;
}

#order-confirmation {
  padding-top: 10px;
  width: 80%;
}

h1 {
  margin-bottom: 2rem;
}

h3 {
  text-decoration: underline;
}

p {
  margin-bottom: 2rem;
  text-align: justify;
}

p:last-child {
  margin-top: 3rem;
}
</style>
