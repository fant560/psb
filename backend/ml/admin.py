from django.contrib import admin
from .models import Document, User, UserDocumentReadState, AudioRecord

# Register your models here.

admin.site.register(Document)