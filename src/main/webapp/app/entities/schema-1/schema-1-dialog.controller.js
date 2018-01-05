(function() {
    'use strict';

    angular
        .module('questProvaApp')
        .controller('Schema1DialogController', Schema1DialogController);

    Schema1DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Schema1', 'SchemaField', 'SchemaAnswer'];

    function Schema1DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Schema1, SchemaField, SchemaAnswer) {
        var vm = this;

        vm.schema1 = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.schemafields = SchemaField.query();
        vm.schemaanswers = SchemaAnswer.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.schema1.id !== null) {
                Schema1.update(vm.schema1, onSaveSuccess, onSaveError);
            } else {
                Schema1.save(vm.schema1, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('questProvaApp:schema1Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.versionDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
