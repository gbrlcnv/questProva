(function() {
    'use strict';

    angular
        .module('questProvaApp')
        .controller('SchemaAnswerDeleteController',SchemaAnswerDeleteController);

    SchemaAnswerDeleteController.$inject = ['$uibModalInstance', 'entity', 'SchemaAnswer'];

    function SchemaAnswerDeleteController($uibModalInstance, entity, SchemaAnswer) {
        var vm = this;

        vm.schemaAnswer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SchemaAnswer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
