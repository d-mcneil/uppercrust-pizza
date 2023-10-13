import Vue from "vue";
import Router from "vue-router";
import Home from "../views/Home.vue";
import EmployeeLogin from "../views/EmployeeLogin.vue";
import Logout from "../views/Logout.vue";
import EmployeeRegister from "../views/EmployeeRegister.vue";
import PendingOrders from "../views/PendingOrders.vue";
import PizzaOptions from "../views/PizzaOptions.vue";
import SpecialtyPizzas from "../views/SpecialtyPizzas.vue";
import store from "../store/index";
import CustomerMenu from "../views/CustomerMenu.vue";
import MyOrder from "../views/MyOrder.vue";
import CustomerCheckout from "../views/CustomerCheckout.vue";
import OrderConfirmation from "../views/OrderConfirmation.vue";
import BuildYourOwnPizza from "../views/BuildYourOwnPizza.vue";
import EmployeeViewOrderPage from "../views/EmployeeViewOrderPage.vue";
import MyAccount from "../views/MyAccount.vue";

Vue.use(Router);

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
	mode: "history",
	base: process.env.BASE_URL,
	routes: [
		{
			path: "/",
			name: "home",
			component: Home,
			meta: {
				requiresAuth: false,
			},
		},
		{
			path: "/my-order",
			name: "my-order",
			component: MyOrder,
			meta: {
				requiresAuth: false,
			},
		},
		{
			path: "/my-account",
			name: "my-account",
			component: MyAccount,
			meta: {
				requiresAuth: true,
			},
		},
		{
			path: "/menu",
			name: "customer-menu",
			component: CustomerMenu,
			meta: {
				requiresAuth: false,
			},
		},
		{
			path: "/customize-pizza/:id",
			name: "build-your-own-pizza",
			component: BuildYourOwnPizza,
			meta: {
				requiresAuth: false,
			},
		},
		{
			path: "/checkout",
			name: "checkout",
			component: CustomerCheckout,
			meta: {
				requiresAuth: false,
			},
		},
		{
			path: "/confirmation",
			name: "confirmation",
			component: OrderConfirmation,
			meta: {
				requiresAuth: false,
			},
		},
		{
			path: "/employees/login",
			name: "employee-login",
			component: EmployeeLogin,
			meta: {
				requiresAuth: false,
			},
		},
		{
			path: "/employees/register",
			name: "employee-register",
			component: EmployeeRegister,
			meta: {
				requiresAuth: true,
			},
		},
		{
			path: "/employees/pending-orders",
			name: "pending-orders",
			component: PendingOrders,
			meta: {
				requiresAuth: true,
			},
		},
		{
			path: "/employees/orders/:id",
			name: "employee-order-view",
			component: EmployeeViewOrderPage,
			meta: {
				requiresAuth: true,
			},
		},
		{
			path: "/employees/specialty-pizzas",
			name: "specialty-pizzas",
			component: SpecialtyPizzas,
			meta: {
				requiresAuth: true,
			},
		},
		{
			path: "/employees/pizza-options",
			name: "pizza-options",
			component: PizzaOptions,
			meta: {
				requiresAuth: true,
			},
		},
		{
			path: "/logout",
			name: "logout",
			component: Logout,
			meta: {
				requiresAuth: false,
			},
		},
	],
});

const sendToCorrectLoginScreen = (to, next) => {
	if (to.path.includes("employees")) {
		next("/employees/login");
	} else {
		next("/");
	}
};

const getRoles = () =>
	store.state.user.authorities.map((role) =>
		role.name.toLowerCase().replace("role_", "")
	);

const isAdmin = (roles) => roles.indexOf("admin") > -1;
const isEmployee = (roles) => roles.indexOf("employee") > -1;

router.beforeEach((to, from, next) => {
	// Determine if the route requires Authentication
	const requiresAuth = to.matched.some((x) => x.meta.requiresAuth);

	if (!requiresAuth) {
		next(); // Let them go to their next destination
	} else if (requiresAuth && store.state.token === "") {
		sendToCorrectLoginScreen(to, next);
	} else {
		const roles = getRoles();

		// Only admins can register new employees
		if (to.name === "employee-register") {
			if (isAdmin(roles)) {
				next();
			} else if (isEmployee(roles)) {
				next("/employees/pending-orders");
			} else {
				next("/");
			}
		}

		// Only admins and employees can access routes with "employees" in them
		if (to.path.includes("employees")) {
			if (isEmployee(roles) || isAdmin(roles)) {
				next();
			} else {
				next("/");
			}
		}

		// Everyone can access the 'my-account' route if they are logged in
		else {
			next();
		}
	}
});

export default router;
