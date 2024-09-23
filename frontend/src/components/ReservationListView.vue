<template>

    <v-data-table
        :headers="headers"
        :items="reservationList"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'ReservationListView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
                { text: "userId", value: "userId" },
                { text: "reserveId", value: "reserveId" },
                { text: "countName", value: "countName" },
                { text: "status", value: "status" },
            ],
            reservationList : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/reservationLists'))

            temp.data._embedded.reservationLists.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.reservationList = temp.data._embedded.reservationLists;
        },
        methods: {
        }
    }
</script>

