<template>
  <section id="my-account" class="customer-section">
    <horizontal-hero />
    <form @submit.prevent="updateUserInfo">
      <h1>Update Account Info</h1>
      <an-alert
        id="disclaimer"
        message="IMPORTANT: DO NOT ENTER YOUR REAL ADDRESS OR PHONE NUMBER. 
			This is a fictitious pizza shop. Payments are not processed, and pizzas are not prepared. However, 
			if you wish to test email confirmation when placing an order, you may enter your real email address so that the sample 
			confirmation message can be delivered to your inbox."
      />
      <an-alert v-if="alertText" :message="alertText" />

      <user-input
        label="Name"
        inputId="my-account-name-input"
        inputType="text"
        v-model="name"
        :defaultValue="name"
        :isRequired="true"
      />

      <user-input
        label="Email"
        inputId="my-account-email-input"
        inputType="email"
        v-model="email"
        :defaultValue="email"
        :isRequired="true"
      />

      <user-input
        label="Phone Number"
        inputId="my-account-phone-input"
        inputType="number"
        v-model="phone"
        :defaultValue="phone"
        :isRequired="true"
      />

      <user-input
        label="Address"
        inputId="my-account-address-input"
        inputType="text"
        v-model="address"
        :defaultValue="address"
        :isRequired="true"
      />
      <user-input
        label="City"
        inputId="my-account-city-input"
        inputType="text"
        v-model="city"
        :defaultValue="city"
        :isRequired="true"
      />
      <user-input
        label="State"
        inputId="my-account-state-input"
        inputType="text"
        v-model="state"
        :defaultValue="state"
        :isRequired="true"
      />
      <user-input
        label="ZIP Code"
        inputId="my-account-zip-input"
        inputType="number"
        v-model="zipCode"
        :defaultValue="zipCode"
        :isRequired="true"
      />
      <!-- <user-input
				label="Credit Card Number"
				inputId="my-account-card-number-input"
				inputType="number"
				v-model="cardNumber"
				:isRequired="true" /> -->

      <small-button buttonText="Save Changes" buttonType="submit" />
    </form>
  </section>
</template>

<script>
import HorizontalHero from "../components/HorizontalHero.vue";
import AnAlert from "../components/AnAlert.vue";
import SmallButton from "../components/SmallButton.vue";
import UserInput from "../components/UserInput.vue";
import userDataService from "../services/UserDataService.js";

export default {
  data() {
    return {
      address: null,
      city: "",
      state: "",
      zipCode: "",
      cardNumber: "",
      dataId: -1,
      email: null,
      phone: 0,
      userId: -1,
      alertText: "",
      name: "",
    };
  },
  created() {
    if (this.$store.state.user.userData) {
      const { address, cardNumber, dataId, email, phone, userId, name } =
        this.$store.state.user.userData;
      if (address) {
        const addressArray = address.split("|||");
        if (addressArray.length === 4) {
          this.address = addressArray[0];
          this.city = addressArray[1];
          this.state = addressArray[2];
          this.zipCode = addressArray[3];
        }
      }
      this.cardNumber = cardNumber;
      this.dataId = dataId;
      this.email = email;
      this.phone = phone;
      this.userId = userId;
      this.name = name;
    }
  },
  methods: {
    updateUserInfo() {
      const {
        address,
        cardNumber,
        dataId,
        email,
        phone,
        userId,
        city,
        state,
        zipCode,
        name,
      } = this;
      const userData = { cardNumber, dataId, email, phone, userId, name };
      userData.address = [address, city, state, zipCode].join("|||");
      userDataService
        .update(userData)
        .then((res) => {
          if (res.status === 200) {
            this.$store.commit("UPDATE_USER", userData);
            this.$router.push({ name: "home" });
          }
        })
        .catch(() =>
          alert(
            "There was an error updating user information. Please try again later."
          )
        );
    },
  },
  components: { UserInput, SmallButton, HorizontalHero, AnAlert },
};
</script>

<style>
section.customer-section#my-account label {
  font-weight: bold;
}
</style>

<style scoped>
div[role="alert"]#disclaimer {
  --text-indent: -6.5rem;
  text-indent: var(--text-indent);
  padding-left: calc((-1 * var(--text-indent)));
  text-align: justify;
}
</style>
