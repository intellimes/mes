package com.qcadoo.mes.materialFlowResources.hooks;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcadoo.mes.advancedGenealogy.criteriaModifier.BatchCriteriaModifier;
import com.qcadoo.mes.basic.CalculationQuantityService;
import com.qcadoo.mes.materialFlowResources.constants.ResourceCorrectionFields;
import com.qcadoo.model.api.Entity;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.FormComponent;
import com.qcadoo.view.api.components.LookupComponent;

@Service
public class ResourceCorrectionDetailsHooks {

    private static final String L_FORM = "form";

    @Autowired
    private CalculationQuantityService calculationQuantityService;

    @Autowired
    private BatchCriteriaModifier batchCriteriaModifier;

    public void onBeforeRender(final ViewDefinitionState view) {
        FormComponent resourceCorrectionForm = (FormComponent) view.getComponentByReference(L_FORM);

        Entity resourceCorrection = resourceCorrectionForm.getPersistedEntityWithIncludedFormValues();

        setBatchLookupsProductFilterValue(view, resourceCorrection);
    }

    private void setBatchLookupsProductFilterValue(final ViewDefinitionState view, final Entity resourceCorrection) {
        LookupComponent oldBatchLookup = (LookupComponent) view.getComponentByReference(ResourceCorrectionFields.OLD_BATCH);
        LookupComponent newBatchLookup = (LookupComponent) view.getComponentByReference(ResourceCorrectionFields.NEW_BATCH);

        Entity product = resourceCorrection.getBelongsToField(ResourceCorrectionFields.PRODUCT);

        if (Objects.nonNull(product)) {
            batchCriteriaModifier.putProductFilterValue(oldBatchLookup, product);
            batchCriteriaModifier.putProductFilterValue(newBatchLookup, product);
        }
    }

}
