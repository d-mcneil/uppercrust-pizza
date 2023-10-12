<template>
	<ul>
		<router-link :to="{ name: 'home' }">
			<li @click="hideDropDownMenu">Home</li>
		</router-link>
		<!-- ********************************** ROUTES FOR CUSTOMER PAGES ********************************** -->
		<router-link :to="{ name: 'customer-menu' }" v-if="!isEmployeePage">
			<li @click="hideDropDownMenu">Menu</li>
		</router-link>
		<router-link :to="{ name: 'my-account' }" v-if="!isEmployeePage && $store.state.token"
			class="jump hide-when-small-screen">
			<li @click="hideDropDownMenu">My Account</li>
		</router-link>
		<router-link :to="{ name: 'my-order' }" v-if="!isEmployeePage && !isOrderPage && Object.keys($store.state.cart).length
			">
			<li @click="hideDropDownMenu">My Order</li>
		</router-link>
		<router-link :to="{ name: 'checkout' }" v-if="isOrderPage">
			<li @click="hideDropDownMenu">Checkout</li>
		</router-link>
		<!-- ********************************** ROUTES FOR EMPLOYEE PAGES ********************************** -->
		<router-link :to="{ name: 'pending-orders' }" v-if="isEmployeePage && isLoggedIn">
			<li @click="hideDropDownMenu">Orders</li>
		</router-link>
		<router-link :to="{ name: 'specialty-pizzas' }" v-if="isEmployeePage && isLoggedIn">
			<li @click="hideDropDownMenu">Pizzas</li>
		</router-link>
		<router-link :to="{ name: 'pizza-options' }" v-if="isEmployeePage && isLoggedIn">
			<li @click="hideDropDownMenu">Options</li>
		</router-link>
		<!-- <router-link
			:to="{ name: 'employee-login' }"
			v-if="isEmployeePage && !isLoggedIn && $route.path.includes('register')"
			
			><li @click="hideDropDownMenu">Login</li></router-link
		> -->
		<router-link :to="{ name: 'employee-register' }" v-if="isEmployeePage &&
			isLoggedIn &&
			$store.state.user.authorities
				.map((role) => role.name.toLowerCase().replace('role_', ''))
				.indexOf('admin') > -1
			">
			<li @click="hideDropDownMenu">Register</li>
		</router-link>

		<!-- ********************************** LOGOUT FOR BOTH PAGES ********************************** -->
		<router-link v-bind:to="{ name: 'logout' }" v-if="isLoggedIn" class="hide-when-small-screen">
			<li @click="hideDropDownMenu">Logout</li>
		</router-link>
	</ul>
</template>

<script>
export default {
	computed: {
		isEmployeePage() {
			return this.$route.path.includes("employees");
		},
		isOrderPage() {
			return this.$route.path.includes("my-order");
		},
		isLoggedIn() {
			return this.$store.state.token != "";
		},
	},
	methods: {
		hideDropDownMenu() {
			this.$store.dispatch("hideDropDownMenu");
		},
	},
};
</script>

<style scoped>
ul,
li {
	margin: 0;
	padding: 0;
}

ul {
	text-align: center;
	--list-item-between-padding: 25px;
	--list-item-horizontal-padding: 25px;
	padding: var(--list-item-horizontal-padding);
}

li {
	color: var(--white-color);
	list-style: none;
	font-family: var(--loud-font-family);
	padding-bottom: var(--list-item-between-padding);
	padding-top: var(--list-item-between-padding);
}

a:not(:first-child) li {
	border-top: 2px solid var(--white-color);
}

a {
	display: block;
	max-width: 20rem;
	width: 100%;
	margin-right: auto;
	margin-left: auto;
}
</style>
