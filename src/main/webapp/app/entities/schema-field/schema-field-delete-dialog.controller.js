(function() {
    'use strict';

    angular
        .module('questProvaApp')
        .controller('SchemaFieldDeleteController',SchemaFieldDeleteController);

    SchemaFieldDeleteController.$inject = ['$uibModalInstance', 'entity', 'SchemaField'];

    function SchemaFieldDeleteController($uibModalInstance, entity, SchemaField) {
        var vm = this;

        vm.schemaField = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SchemaField.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
