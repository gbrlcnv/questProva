(function() {
    'use strict';

    angular
        .module('questProvaApp')
        .controller('Schema1DeleteController',Schema1DeleteController);

    Schema1DeleteController.$inject = ['$uibModalInstance', 'entity', 'Schema1'];

    function Schema1DeleteController($uibModalInstance, entity, Schema1) {
        var vm = this;

        vm.schema1 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Schema1.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
