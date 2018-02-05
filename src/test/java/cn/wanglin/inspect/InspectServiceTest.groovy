package cn.wanglin.inspect

import org.junit.Before
import org.junit.Test
import static org.mockito.Mockito.*

class InspectServiceTest {
    InspectService inspectService = new InspectService()

    @Before
    public void setup() {
        ParamsService paramsService = mock(ParamsService.class);
        GroovyService groovyService = mock(GroovyService.class);

        inspectService.groovyService = groovyService
        inspectService.paramsService = paramsService;
        inspectService.resultProcessor = mock(ResultProcessor.class)
        inspectService.contextService = new ContextService()
        inspectService.contextService.paramsService = paramsService;

        when(groovyService.getObject(null,VarHandler.class)).thenReturn(new ContextHandler())

        when(paramsService.getType("test")).thenReturn(new EventType(name: "test"))
        when(paramsService.getVar("data")).thenReturn(new Var(name: "data"))
        when(paramsService.getRulesOfType("test")).thenReturn([new Rule(engine: EngineType.EL,expression: "data.amount <5000")] as Set)
        when(inspectService.resultProcessor.process(any())).thenReturn(new InnerResult() {
            @Override
            Boolean isReject() {
                return true
            }
        })
    }

    @Test
    void testSync() {
        inspectService.inspect("test", "1", [amount: 1, name: "wanglin"])
    }
}
