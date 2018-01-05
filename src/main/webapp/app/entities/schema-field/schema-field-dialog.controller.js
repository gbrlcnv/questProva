(function() {
    'use strict';

    angular
        .module('questProvaApp')
        .controller('SchemaFieldDialogController', SchemaFieldDialogController);

    SchemaFieldDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SchemaField', 'Schema1'];

    function SchemaFieldDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SchemaField, Schema1) {
        var vm = this;

        vm.schemaField = entity;
        vm.clear = clear;
        vm.save = save;
        vm.schema1s = Schema1.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.schemaField.id !== null) {
                SchemaField.update(vm.schemaField, onSaveSuccess, onSaveError);
            } else {
                SchemaField.save(vm.schemaField, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('questProvaApp:schemaFieldUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
