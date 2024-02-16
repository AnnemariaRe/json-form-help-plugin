package com.solanteq.solar.plugin.dsl.converter.action

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.builder.action.PostActionBuilder
import com.solanteq.solar.plugin.json.schema.action.PostAction
import org.springframework.stereotype.Service

@Service
class PostActionDslConverter : AbstractDslConverter<PostAction, PostActionBuilder>() {

    override fun toDslElement(modelElement: PostAction): PostActionBuilder {
        return with(modelElement) {
            PostActionBuilder(
                showSuccess,
                reloadOnComplete,
                reloadType,
                reloadGroups
            )
        }
    }

}