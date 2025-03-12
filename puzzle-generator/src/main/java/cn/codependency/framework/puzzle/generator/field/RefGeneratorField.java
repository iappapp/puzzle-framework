package cn.codependency.framework.puzzle.generator.field;

import cn.codependency.framework.puzzle.generator.config.Extend;
import cn.codependency.framework.puzzle.generator.config.ListGeneratorFieldType;
import cn.codependency.framework.puzzle.generator.config.ModelDefinition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RefGeneratorField extends SimpleGeneratorField {

    private ModelDefinition refModel;


    public RefGeneratorField(String refField, ModelDefinition refModel, String label) {
        this(refField, refModel, label, false);
    }

    public RefGeneratorField(String refField, ModelDefinition refModel, String label, boolean list) {
        super(refField, list ? new ListGeneratorFieldType(refModel.getIdType()) : refModel.getIdType(), label);
        this.refModel = refModel;
    }

    public RefGeneratorField(String refField, ModelDefinition refModel, String label, boolean list, boolean internal) {
        super(refField, list ? new ListGeneratorFieldType(refModel.getIdType()) : refModel.getIdType(), label);
        this.refModel = refModel;
        this.setExtend(new Extend().setInternal(internal));
    }
}

