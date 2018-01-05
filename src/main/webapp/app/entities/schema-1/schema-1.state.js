(function() {
    'use strict';

    angular
        .module('questProvaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('schema-1', {
            parent: 'entity',
            url: '/schema-1?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'questProvaApp.schema1.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/schema-1/schema-1-s.html',
                    controller: 'Schema1Controller',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('schema1');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('schema-1-detail', {
            parent: 'schema-1',
            url: '/schema-1/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'questProvaApp.schema1.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/schema-1/schema-1-detail.html',
                    controller: 'Schema1DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('schema1');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Schema1', function($stateParams, Schema1) {
                    return Schema1.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'schema-1',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('schema-1-detail.edit', {
            parent: 'schema-1-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/schema-1/schema-1-dialog.html',
                    controller: 'Schema1DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Schema1', function(Schema1) {
                            return Schema1.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('schema-1.new', {
            parent: 'schema-1',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/schema-1/schema-1-dialog.html',
                    controller: 'Schema1DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                version: null,
                                versionDate: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('schema-1', null, { reload: 'schema-1' });
                }, function() {
                    $state.go('schema-1');
                });
            }]
        })
        .state('schema-1.edit', {
            parent: 'schema-1',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/schema-1/schema-1-dialog.html',
                    controller: 'Schema1DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Schema1', function(Schema1) {
                            return Schema1.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('schema-1', null, { reload: 'schema-1' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('schema-1.delete', {
            parent: 'schema-1',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/schema-1/schema-1-delete-dialog.html',
                    controller: 'Schema1DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Schema1', function(Schema1) {
                            return Schema1.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('schema-1', null, { reload: 'schema-1' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
