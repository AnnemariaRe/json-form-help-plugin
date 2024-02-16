package com.solanteq.solar.plugin.dsl.converter.group.row

import com.solanteq.solar.plugin.converter.AbstractDslConverter
import com.solanteq.solar.plugin.dsl.builder.group.row.RowStyleBuilder
import com.solanteq.solar.plugin.json.schema.group.inline.RowStyle
import org.springframework.stereotype.Service

@Service
class RowStyleDslConverter : AbstractDslConverter<RowStyle, RowStyleBuilder>() {

    override fun toDslElement(modelElement: RowStyle): RowStyleBuilder {
        return with(modelElement) {
            RowStyleBuilder(
                info,
                success,
                warning,
                error,
                muted
            )
        }
    }
}