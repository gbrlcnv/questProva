'use strict';

describe('Controller Tests', function() {

    describe('Schema1 Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSchema1, MockSchemaField, MockSchemaAnswer;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSchema1 = jasmine.createSpy('MockSchema1');
            MockSchemaField = jasmine.createSpy('MockSchemaField');
            MockSchemaAnswer = jasmine.createSpy('MockSchemaAnswer');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Schema1': MockSchema1,
                'SchemaField': MockSchemaField,
                'SchemaAnswer': MockSchemaAnswer
            };
            createController = function() {
                $injector.get('$controller')("Schema1DetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'questProvaApp:schema1Update';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
