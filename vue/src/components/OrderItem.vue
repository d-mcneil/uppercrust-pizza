<template>
  <div class="pendingList">
    <div>
      <span>Order #{{ currentOrder.orderId }}</span>
      <br />
      <span>{{ currentOrder.isDelivery ? "Delivery" : "Pick-up" }}</span>
      <br />
    </div>

    <router-link
      v-bind:to="{
        name: 'employee-order-view',
        params: { id: currentOrder.orderId },
      }"
    >
      <small-button buttonText="View" />
    </router-link>

    <select v-if="isLoaded" v-model="currentOrder.status" @change="saveChanges">
      <option
        v-for="stat in possibleStatus"
        :key="stat"
        :value="stat.toLowerCase()"
      >
        {{ stat }}
      </option>
    </select>
  </div>
</template>

<script>
import OrderService from "../services/OrderService";
import SmallButton from "./SmallButton.vue";

export default {
  components: { SmallButton },

  data() {
    return {
      possibleStatus: ["Pending", "Cancelled", "Ready", "Complete"],

      currentOrder: {
        orderId: -1,
        status: "",
        email: "",
        address: "",
        phone: -1,
        isDelivery: false,
        menuItems: {},
        customPizzas: {},
      },
      isLoaded: false,
    };
  },

  props: ["order"],

  created() {
    this.initializeOrder();
    this.isLoaded = true;
  },

  methods: {
    initializeOrder() {
      const {
        orderId,
        status,
        email,
        address,
        phone,
        isDelivery,
        menuItems,
        customPizzas,
      } = this.order;
      this.currentOrder.orderId = orderId;
      this.currentOrder.status = status;
      this.currentOrder.email = email;
      this.currentOrder.address = address;
      this.currentOrder.phone = phone;
      this.currentOrder.isDelivery = isDelivery;
      this.currentOrder.menuItems = menuItems;
      this.currentOrder.customPizzas = customPizzas;
    },

    saveChanges() {
      // ******************* THE DEMO EMPLOYEE ACCOUNT CAN'T MAKE ANY CHANGES, SO ALERT AND PREVENT FROM MAKING REQUEST
      if (
        this.$store.state.user.authorities
          .map((role) => role.name.toLowerCase().replace("role_", ""))
          .indexOf("demo_employee") > -1
      ) {
        alert(
          "The demo employee account cannot make any changes to existing data or create new data."
        );
        return;
      }
      // **************************************************************************************************************

      const orderId = this.currentOrder.orderId;
      const status = this.currentOrder.status;
      OrderService.updatePendingOrder({ orderId, status }).catch(() =>
        alert(
          "There was an error updating the status of the order. Please try again later."
        )
      );
    },
  },
};
</script>

<style scoped>
div.pendingList {
  display: grid;
  grid-template-columns: 2fr 2fr 1fr;
  width: 80%;
  justify-content: center;
  padding: 15px 10px;
  margin: 10px auto;
  border-bottom: black solid 3px;
}
</style>
