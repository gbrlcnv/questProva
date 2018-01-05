(function() {
    'use strict';

    angular
        .module('questProvaApp')
        .controller('SchemaAnswerFieldDeleteController',SchemaAnswerFieldDeleteController);

    SchemaAnswerFieldDeleteController.$inject = ['$uibModalInstance', 'entity', 'SchemaAnswerField'];

    function SchemaAnswerFieldDeleteController($uibModalInstance, entity, SchemaAnswerField) {
        var vm = this;

        vm.schemaAnswerField = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SchemaAnswerField.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
