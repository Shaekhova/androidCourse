package com.teacherhelper.model

import com.google.api.client.util.Key
import com.kinvey.java.LinkedResources.LinkedGenericJson


class ClassEntity : LinkedGenericJson() {
    @Key("_id")
    var id: String? = null
    @Key("name")
    var name: String? = null
    @Key("teacher_id")
    var teacherId: String? = null
}
