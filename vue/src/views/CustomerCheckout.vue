<template>
  <section class="customer-section" id="checkout-section">
    <horizontal-hero></horizontal-hero>
    <div class="checkout-inputs">
      <h1>Checkout</h1>
      <an-alert
        id="disclaimer"
        message="IMPORTANT: DO NOT ENTER YOUR REAL CARD INFORMATION, ADDRESS, OR PHONE NUMBER. 
			This is a fictitious pizza shop. Payments are not processed, and pizzas are not prepared. However, 
			if you wish to test the email confirmation, you may enter your real email address so that the sample 
			confirmation message can be delivered to your inbox."
      />
      <form class="checkout-wrapper" @submit.prevent="placeOrder">
        <user-input
          :defaultValue="cardholderName"
          label="Cardholder Name"
          inputId="cardholder-name"
          inputType="text"
          :isRequired="true"
          v-model="cardholderName"
        />

        <user-input
          :isRequired="true"
          label="Card Number"
          inputId="card-number"
          inputType="number"
          v-model="cardNumber"
        />

        <user-input
          :isRequired="true"
          label="Expiration Date"
          inputId="exp-date"
          inputType="text"
          v-model="expDate"
        />

        <user-input
          :isRequired="true"
          label="Security Code (CVC)"
          inputId="cvc"
          inputType="number"
          v-model="cvc"
        />

        <user-input
          :defaultValue="address"
          :isRequired="true"
          label="Delivery Address"
          inputId="address"
          inputType="text"
          v-model="address"
        />

        <user-input
          :isRequired="true"
          :defaultValue="city"
          label="City"
          inputId="city"
          inputType="text"
          v-model="city"
        />

        <user-input
          :defaultValue="state"
          :isRequired="true"
          label="State"
          inputId="state"
          inputType="text"
          v-model="state"
        />

        <user-input
          :defaultValue="zipCode"
          :isRequired="true"
          label="ZIP Code"
          inputId="zip-code"
          inputType="number"
          v-model="zipCode"
        />

        <user-input
          :defaultValue="phoneNumber"
          :isRequired="true"
          label="Phone Number"
          inputId="phone-number"
          inputType="number"
          v-model="phoneNumber"
        />

        <user-input
          :defaultValue="email"
          :isRequired="true"
          label="Email"
          inputId="email"
          inputType="email"
          v-model="email"
        />

        <div class="delivery">
          <p>How would you like to receive your pizza?</p>
          <div>
            <input
              type="radio"
              id="pick-up"
              name="delivery"
              v-model="isDelivery"
              :value="false"
            />
            <label for="pick-up">Pick-up</label>
          </div>
          <div>
            <input
              type="radio"
              id="delivery"
              name="delivery"
              v-model="isDelivery"
              :value="true"
            />
            <label for="delivery">Delivery</label>
          </div>
        </div>

        <checkout-amount buttonText="Place Order" buttonType="submit" />
        <call-to-action-buttons :formSubmissionButton="true" />
      </form>
    </div>
  </section>
</template>

<script>
import UserInput from "../components/UserInput.vue";
import HorizontalHero from "../components/HorizontalHero.vue";
import orderService from "../services/OrderService.js";
import CheckoutAmount from "../components/CheckoutAmount.vue";
import CallToActionButtons from "../components/CallToActionButtons.vue";
import AnAlert from "../components/AnAlert.vue";
import emailjs from "emailjs-com";

export default {
  components: {
    UserInput,
    HorizontalHero,
    CheckoutAmount,
    CallToActionButtons,
    AnAlert,
  },
  data() {
    return {
      cardholderName: "",
      cardNumber: "",
      expDate: "",
      cvc: "",
      address: "",
      city: "",
      state: "",
      zipCode: "",
      isDelivery: false,
      email: "",
      phoneNumber: "",
    };
  },
  created() {
    if (this.$store.state.token && this.$store.state.user.userData) {
      const { phone, email, address, name } = this.$store.state.user.userData;
      this.phoneNumber = phone;
      this.email = email;
      this.cardholderName = name;
      if (address) {
        const addressArray = address.split("|||");
        if (addressArray.length === 4) {
          this.address = addressArray[0];
          this.city = addressArray[1];
          this.state = addressArray[2];
          this.zipCode = addressArray[3];
        }
      }
    }
  },
  methods: {
    placeOrder() {
      const order = {};

      order.name = this.cardholderName;
      order.isDelivery = this.isDelivery;
      order.address =
        this.address +
        "|||" +
        this.city +
        "|||" +
        this.state +
        "|||" +
        this.zipCode;
      order.phone = this.phoneNumber;
      order.email = this.email;
      order.subtotal = this.$store.state.subtotal;
      order.tax = this.tax;

      order.menuItems = [];
      const menuItems = Object.values(this.$store.state.cart).filter(
        (menuItem) => menuItem.id > 0
      );
      for (const menuItem of menuItems) {
        order.menuItems.push({
          itemId: menuItem.id,
          quantity: menuItem.quantity,
        });
      }

      order.customPizzas = [];
      const customPizzas = Object.values(this.$store.state.cart).filter(
        (customPizza) => customPizza.id < 0
      );
      for (const customPizza of customPizzas) {
        const { quantity, toppings, size, sauce, crust } = customPizza;

        order.customPizzas.push({
          quantity,
          sauce: { id: sauce.id },
          crust: { id: crust.id },
          size: { id: size.id },
          toppings: toppings.map((toppingId) => ({ id: toppingId })),
        });
      }

      orderService
        .createPendingOrder(order)
        .then((res) => {
          if (res.status === 200) {
            this.$store.commit(
              "ADD_CONFIRMED_ORDER_TO_SHOW_ON_ORDER_CONFIRMATION_VIEW",
              order
            );
            this.$router.push({ name: "confirmation" });
            this.sendEmail();
          }
        })
        .catch(() =>
          alert("There was an error placing the order. Please try again later.")
        );
    },

    sendEmail() {
      try {
        emailjs.send(
          "service_yyqznlp",
          "template_vcmxkqq",
          {
            to_name: this.cardholderName,
            email: this.email,
            from_name: "UpperCrust Pizza",
            message:
              "Thank you for ordering from UpperCrust Pizza! This is a fictitious pizza shop, so no payment was processed, and pizzas will not be prepared. Have a good day!",
          },
          "77XdGvOEdfvrk3zku" // public key
        );
      } catch (error) {
        console.log({ error });
      }
    },
  },

  computed: {
    total() {
      return this.$store.state.subtotal + this.tax;
    },

    tax() {
      return this.$store.state.subtotal * 0.08;
    },
  },
  beforeCreate() {
    if (!Object.keys(this.$store.state.cart).length) {
      this.$router.push({ name: "customer-menu" });
    }
  },
};
</script>

<style scoped>
div[role="alert"]#disclaimer {
  --text-indent: -6.5rem;
  max-width: 48rem;
  margin-right: auto;
  margin-left: auto;
  text-indent: var(--text-indent);
  padding-left: calc((-1 * var(--text-indent)) + 1.25rem);
  padding-right: 1.25rem;
  text-align: justify;
}

@media only screen and (min-width: 600px) {
  section.customer-section#checkout-section {
    max-width: min(80%, 60rem);
  }
}

.delivery > div {
  padding-top: 5px;
}

.checkout-wrapper .form-input-group {
  padding-right: 20px;
  padding-left: 20px;
}

.place-order-button {
  display: block;
  text-align: right;
  margin-right: 195px;
}

.checkout-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
}

.instructions {
  height: 100px;
  width: 300px;
}

.delivery {
  display: flex;
  flex-direction: column;
  width: 100%;
  text-align: center;
}
</style>
