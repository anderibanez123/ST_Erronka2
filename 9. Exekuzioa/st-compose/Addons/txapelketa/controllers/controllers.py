# -*- coding: utf-8 -*-
# from odoo import http


# class Txapelketa(http.Controller):
#     @http.route('/txapelketa/txapelketa', auth='public')
#     def index(self, **kw):
#         return "Hello, world"

#     @http.route('/txapelketa/txapelketa/objects', auth='public')
#     def list(self, **kw):
#         return http.request.render('txapelketa.listing', {
#             'root': '/txapelketa/txapelketa',
#             'objects': http.request.env['txapelketa.txapelketa'].search([]),
#         })

#     @http.route('/txapelketa/txapelketa/objects/<model("txapelketa.txapelketa"):obj>', auth='public')
#     def object(self, obj, **kw):
#         return http.request.render('txapelketa.object', {
#             'object': obj
#         })
