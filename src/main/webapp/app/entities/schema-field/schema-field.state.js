(function() {
    'use strict';

    angular
        .module('questProvaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('schema-field', {
            parent: 'entity',
            url: '/schema-field',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'questProvaApp.schemaField.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/schema-field/schema-fields.html',
                    controller: 'SchemaFieldController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('schemaField');
                    $translatePartialLoader.addPart('typeEnum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('schema-field-detail', {
            parent: 'schema-field',
            url: '/schema-field/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'questProvaApp.schemaField.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/schema-field/schema-field-detail.html',
                    controller: 'SchemaFieldDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('schemaField');
                    $translatePartialLoader.addPart('typeEnum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SchemaField', function($stateParams, SchemaField) {
                    return SchemaField.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'schema-field',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('schema-field-detail.edit', {
            parent: 'schema-field-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/schema-field/schema-field-dialog.html',
                    controller: 'SchemaFieldDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SchemaField', function(SchemaField) {
                            return SchemaField.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('schema-field.new', {
            parent: 'schema-field',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/schema-field/schema-field-dialog.html',
                    controller: 'SchemaFieldDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                description: null,
                                order: null,
                                type: null,
                                valueListCode: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('schema-field', null, { reload: 'schema-field' });
                }, function() {
                    $state.go('schema-field');
                });
            }]
        })
        .state('schema-field.edit', {
            parent: 'schema-field',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/schema-field/schema-field-dialog.html',
                    controller: 'SchemaFieldDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SchemaField', function(SchemaField) {
                            return SchemaField.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('schema-field', null, { reload: 'schema-field' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('schema-field.delete', {
            parent: 'schema-field',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/schema-field/schema-field-delete-dialog.html',
                    controller: 'SchemaFieldDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SchemaField', function(SchemaField) {
                            return SchemaField.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('schema-field', null, { reload: 'schema-field' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
