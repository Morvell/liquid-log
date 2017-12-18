package ru.naumen.sd40.log.parser;

import org.junit.Assert;
import org.junit.Test;
import ru.naumen.sd40.log.parser.sdng.ActionDoneData;
import ru.naumen.sd40.log.parser.sdng.ActionDoneParser;

public class ActionDoneParserTest {

    @Test
    public void mustParseAddAction() {
        //given
        ActionDoneParser parser = new ActionDoneParser();
        ActionDoneData data = new ActionDoneData();

        //when
        parser.parseLine(data,"Done(10): AddObjectAction");

        //then
        Assert.assertEquals(1, data.getAddObjectActions());
    }

    @Test
    public void mustParseFormActions() {
        //given
        ActionDoneParser parser = new ActionDoneParser();
        ActionDoneData data = new ActionDoneData();

        //when
        parser.parseLine(data,"Done(10): GetFormAction");
        parser.parseLine(data,"Done(1): GetAddFormContextDataAction");

        //then
        Assert.assertEquals(2, data.getFormActions());
    }

    @Test
    public void mustParseEditObject() {
        //given
        ActionDoneParser parser=  new ActionDoneParser();
        ActionDoneData data = new ActionDoneData();

        //when
        parser.parseLine(data,"Done(10): EditObjectAction");

        //then
        Assert.assertEquals(1, data.getEditObjectsActions());
    }

    @Test
    public void mustParseSearchObject(){
        //given
        ActionDoneParser parser = new ActionDoneParser();
        ActionDoneData data = new ActionDoneData();

        //when
        parser.parseLine(data,"Done(10): GetPossibleAgreementsChildsSearchAction");
        parser.parseLine(data,"Done(10): TreeSearchAction");
        parser.parseLine(data,"Done(10): GetSearchResultAction");
        parser.parseLine(data,"Done(10): GetSimpleSearchResultsAction");
        parser.parseLine(data,"Done(10): SimpleSearchAction");
        parser.parseLine(data,"Done(10): ExtendedSearchByStringAction");
        parser.parseLine(data,"Done(10): ExtendedSearchByFilterAction");

        //then
        Assert.assertEquals(7, data.getSearchActions());
    }

    @Test
    public void mustParseGetList(){
        //given:
        ActionDoneParser parser=  new ActionDoneParser();
        ActionDoneData data = new ActionDoneData();

        //when:
        parser.parseLine(data,"Done(10): GetDtObjectListAction");
        parser.parseLine(data,"Done(10): GetPossibleCaseListValueAction");
        parser.parseLine(data,"Done(10): GetPossibleAgreementsTreeListActions");
        parser.parseLine(data,"Done(10): GetCountForObjectListAction");
        parser.parseLine(data,"Done(10): GetDataForObjectListAction");
        parser.parseLine(data,"Done(10): GetPossibleAgreementsListActions");
        parser.parseLine(data,"Done(10): GetDtObjectForRelObjListAction");

        //then:
        Assert.assertEquals(7, data.geListActions());
    }

    @Test
    public void mustParseComment(){
        //given:
        ActionDoneParser parser=  new ActionDoneParser();
        ActionDoneData data = new ActionDoneData();

        //when:
        parser.parseLine(data,"Done(10): EditCommentAction");
        parser.parseLine(data,"Done(10): ChangeResponsibleWithAddCommentAction");
        parser.parseLine(data,"Done(10): ShowMoreCommentAttrsAction");
        parser.parseLine(data,"Done(10): CheckObjectsExceedsCommentsAmountAction");
        parser.parseLine(data,"Done(10): GetAddCommentPermissionAction");
        parser.parseLine(data,"Done(10): GetCommentDtObjectTemplateAction");

        //then:
        Assert.assertEquals(6, data.getCommentActions());
    }

    @Test
    public void mustParseDtObject(){
        //given:
        ActionDoneParser parser=  new ActionDoneParser();
        ActionDoneData data = new ActionDoneData();

        //when:
        parser.parseLine(data,"Done(10): GetVisibleDtObjectAction");
        parser.parseLine(data,"Done(10): GetDtObjectsAction");
        parser.parseLine(data,"Done(10): GetDtObjectTreeSelectionStateAction");
        parser.parseLine(data,"Done(10): AbstractGetDtObjectTemplateAction");
        parser.parseLine(data,"Done(10): GetDtObjectTemplateAction");

        //then:
        Assert.assertEquals(5, data.getDtObjectActions());
    }

}
