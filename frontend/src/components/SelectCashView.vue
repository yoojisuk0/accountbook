<template>

    <v-data-table
        :headers="headers"
        :items="selectCash"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'SelectCashView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
            ],
            selectCash : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/selectCashes'))

            temp.data._embedded.selectCashes.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.selectCash = temp.data._embedded.selectCashes;
        },
        methods: {
        }
    }
</script>

