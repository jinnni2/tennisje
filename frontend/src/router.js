
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);


import ReserveReserveManager from "./components/listers/ReserveReserveCards"
import ReserveReserveDetail from "./components/listers/ReserveReserveDetail"

import MachineMachineManager from "./components/listers/MachineMachineCards"
import MachineMachineDetail from "./components/listers/MachineMachineDetail"

import CourtCourtManager from "./components/listers/CourtCourtCards"
import CourtCourtDetail from "./components/listers/CourtCourtDetail"



export default new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: [
            {
                path: '/reserves/reserves',
                name: 'ReserveReserveManager',
                component: ReserveReserveManager
            },
            {
                path: '/reserves/reserves/:id',
                name: 'ReserveReserveDetail',
                component: ReserveReserveDetail
            },

            {
                path: '/machines/machines',
                name: 'MachineMachineManager',
                component: MachineMachineManager
            },
            {
                path: '/machines/machines/:id',
                name: 'MachineMachineDetail',
                component: MachineMachineDetail
            },

            {
                path: '/courts/courts',
                name: 'CourtCourtManager',
                component: CourtCourtManager
            },
            {
                path: '/courts/courts/:id',
                name: 'CourtCourtDetail',
                component: CourtCourtDetail
            },




    ]
})
