
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import AccountAccountManager from "./components/listers/AccountAccountCards"
import AccountAccountDetail from "./components/listers/AccountAccountDetail"






export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/accounts/accounts',
                name: 'AccountAccountManager',
                component: AccountAccountManager
            },
            {
                path: '/accounts/accounts/:id',
                name: 'AccountAccountDetail',
                component: AccountAccountDetail
            },







    ]
})
