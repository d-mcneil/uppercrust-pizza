import axios from "axios";
import store from "../store/index.js";

export default {
	getPendingOrders() {
		return axios.get("/orders/status?status=pending").then(
			(res) => {
				if (res.status == 200) {
					store.commit("LOAD_PENDING_ORDERS", res.data)
				}
			}
		);
	},
	getDemoPendingOrders() {
		return axios.get("/demo-orders/status?status=pending").then(
			(res) => {
				if (res.status == 200) {
					store.commit("LOAD_PENDING_ORDERS", res.data)
				}
			}
		);
	},
	createPendingOrder(order) {
		return axios.post("/orders", order);
	},
	updatePendingOrder(orderStatus) {
		return axios.put("/orders", orderStatus);
	},
};
