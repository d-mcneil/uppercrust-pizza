<template>
  <section id="pending-orders" class="employee-section">
    <horizontal-hero></horizontal-hero>
    <div class="pending-order-wrapper">
      <h1>Pending Orders</h1>
      <order-item
        v-for="order of this.$store.state.pendingOrders"
        :key="order.id"
        :order="order"
      />
    </div>
  </section>
</template>

<script>
import HorizontalHero from "../components/HorizontalHero.vue";
import OrderService from "../services/OrderService";
import OrderItem from "../components/OrderItem";

export default {
  components: { HorizontalHero, OrderItem },
  name: "pending-orders",
  beforeCreate() {
    if (Object.keys(this.$store.state.cart).length) {
      this.$store.commit("CLEAR_CART");
    }

    // If the user is logged in as the demo employee, a different request is made so that only the demo orders are returned.
    const roles = this.$store.state.user.authorities.map((role) =>
      role.name.toLowerCase().replace("role_", "")
    );

    const isDemoEmployee = roles.indexOf("demo_employee") > -1;

    const apiRequest = isDemoEmployee
      ? OrderService.getDemoPendingOrders
      : OrderService.getPendingOrders;

    apiRequest().catch(() =>
      alert(
        "There was an error retrieving pending orders. Please try again later."
      )
    );
  },
};
</script>

<style></style>
