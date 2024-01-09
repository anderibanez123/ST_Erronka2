# -*- coding: utf-8 -*-

from odoo import models, fields, api


class txapelketa(models.Model):
    _name = 'txapelketa.txapelketa'
    _description = 'txapelketa.txapelketa'

    izena = fields.Char()
    abizena = fields.Char()
    nan = fields.Text()
    puntuaketa = fields.Integer()
    denbora = fields.Integer()
 
