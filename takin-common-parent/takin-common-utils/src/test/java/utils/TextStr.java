package utils;

/**
 * @author pnz.zhao
 * @Description:
 * @date 2022/2/23 10:43
 */
public class TextStr {
    public static String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<jmeterTestPlan version=\"1.2\" properties=\"5.0\" jmeter=\"5.0 r1840935\">\n" +
            "  <hashTree>\n" +
            "    <TestPlan guiclass=\"TestPlanGui\" testclass=\"TestPlan\" testname=\"测试计划\" enabled=\"true\">\n" +
            "      <stringProp name=\"TestPlan.comments\"></stringProp>\n" +
            "      <boolProp name=\"TestPlan.serialize_threadgroups\">false</boolProp>\n" +
            "      <stringProp name=\"TestPlan.user_define_classpath\"></stringProp>\n" +
            "      <boolProp name=\"TestPlan.functional_mode\">false</boolProp>\n" +
            "      <boolProp name=\"TestPlan.tearDown_on_shutdown\">true</boolProp>\n" +
            "      <elementProp name=\"TestPlan.user_defined_variables\" elementType=\"Arguments\" guiclass=\"ArgumentsPanel\" testclass=\"Arguments\" testname=\"用户定义的变量\" enabled=\"true\">\n" +
            "        <collectionProp name=\"Arguments.arguments\"/>\n" +
            "      </elementProp>\n" +
            "    </TestPlan>\n" +
            "    <hashTree>\n" +
            "      <ResultCollector guiclass=\"ViewResultsFullVisualizer\" testclass=\"ResultCollector\" testname=\"察看结果树\" enabled=\"true\">\n" +
            "        <boolProp name=\"ResultCollector.error_logging\">false</boolProp>\n" +
            "        <objProp>\n" +
            "          <name>saveConfig</name>\n" +
            "          <value class=\"SampleSaveConfiguration\">\n" +
            "            <time>true</time>\n" +
            "            <latency>true</latency>\n" +
            "            <timestamp>true</timestamp>\n" +
            "            <success>true</success>\n" +
            "            <label>true</label>\n" +
            "            <code>true</code>\n" +
            "            <message>true</message>\n" +
            "            <threadName>true</threadName>\n" +
            "            <dataType>true</dataType>\n" +
            "            <encoding>false</encoding>\n" +
            "            <assertions>true</assertions>\n" +
            "            <subresults>true</subresults>\n" +
            "            <responseData>true</responseData>\n" +
            "            <samplerData>false</samplerData>\n" +
            "            <xml>false</xml>\n" +
            "            <fieldNames>true</fieldNames>\n" +
            "            <responseHeaders>false</responseHeaders>\n" +
            "            <requestHeaders>false</requestHeaders>\n" +
            "            <responseDataOnError>false</responseDataOnError>\n" +
            "            <saveAssertionResultsFailureMessage>true</saveAssertionResultsFailureMessage>\n" +
            "            <assertionsResultsToSave>0</assertionsResultsToSave>\n" +
            "            <bytes>true</bytes>\n" +
            "            <sentBytes>true</sentBytes>\n" +
            "            <url>true</url>\n" +
            "            <threadCounts>true</threadCounts>\n" +
            "            <idleTime>true</idleTime>\n" +
            "            <connectTime>true</connectTime>\n" +
            "          </value>\n" +
            "        </objProp>\n" +
            "        <stringProp name=\"filename\"></stringProp>\n" +
            "      </ResultCollector>\n" +
            "      <hashTree/>\n" +
            "      <ThreadGroup guiclass=\"ThreadGroupGui\" testclass=\"ThreadGroup\" testname=\"test01线程组\" enabled=\"true\">\n" +
            "        <stringProp name=\"ThreadGroup.num_threads\">1</stringProp>\n" +
            "        <boolProp name=\"ThreadGroup.scheduler\">false</boolProp>\n" +
            "        <boolProp name=\"ThreadGroup.same_user_on_next_iteration\">true</boolProp>\n" +
            "        <elementProp name=\"ThreadGroup.main_controller\" elementType=\"LoopController\" guiclass=\"LoopControlPanel\" testclass=\"LoopController\" testname=\"循环控制器\" enabled=\"true\">\n" +
            "          <boolProp name=\"LoopController.continue_forever\">false</boolProp>\n" +
            "          <stringProp name=\"LoopController.loops\">1</stringProp>\n" +
            "        </elementProp>\n" +
            "        <stringProp name=\"ThreadGroup.delay\"></stringProp>\n" +
            "        <stringProp name=\"ThreadGroup.duration\"></stringProp>\n" +
            "        <stringProp name=\"ThreadGroup.on_sample_error\">continue</stringProp>\n" +
            "        <stringProp name=\"ThreadGroup.ramp_time\"></stringProp>\n" +
            "      </ThreadGroup>\n" +
            "      <hashTree>\n" +
            "        <HTTPSamplerProxy guiclass=\"HttpTestSampleGui\" testclass=\"HTTPSamplerProxy\" testname=\"HTTP请求\" enabled=\"true\">\n" +
            "          <elementProp name=\"HTTPsampler.Arguments\" elementType=\"Arguments\" guiclass=\"HTTPArgumentsPanel\" testclass=\"Arguments\" testname=\"用户定义的变量\" enabled=\"true\">\n" +
            "            <collectionProp name=\"Arguments.arguments\">\n" +
            "              <elementProp name=\"param\" elementType=\"HTTPArgument\">\n" +
            "                <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
            "                <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
            "                <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
            "                <stringProp name=\"Argument.name\">param</stringProp>\n" +
            "                <stringProp name=\"Argument.value\">12321321321</stringProp>\n" +
            "              </elementProp>\n" +
            "              <elementProp name=\"time\" elementType=\"HTTPArgument\">\n" +
            "                <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
            "                <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
            "                <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
            "                <stringProp name=\"Argument.name\">time</stringProp>\n" +
            "                <stringProp name=\"Argument.value\">100</stringProp>\n" +
            "              </elementProp>\n" +
            "            </collectionProp>\n" +
            "          </elementProp>\n" +
            "          <stringProp name=\"HTTPSampler.domain\">192.168.1.186</stringProp>\n" +
            "          <stringProp name=\"HTTPSampler.port\">8080</stringProp>\n" +
            "          <stringProp name=\"HTTPSampler.protocol\">http</stringProp>\n" +
            "          <stringProp name=\"HTTPSampler.contentEncoding\"></stringProp>\n" +
            "          <stringProp name=\"HTTPSampler.path\">/pt/test_02?abc=123</stringProp>\n" +
            "          <stringProp name=\"HTTPSampler.method\">GET</stringProp>\n" +
            "          <boolProp name=\"HTTPSampler.follow_redirects\">true</boolProp>\n" +
            "          <boolProp name=\"HTTPSampler.auto_redirects\">false</boolProp>\n" +
            "          <boolProp name=\"HTTPSampler.use_keepalive\">true</boolProp>\n" +
            "          <boolProp name=\"HTTPSampler.DO_MULTIPART_POST\">false</boolProp>\n" +
            "          <stringProp name=\"HTTPSampler.embedded_url_re\"></stringProp>\n" +
            "          <stringProp name=\"HTTPSampler.connect_timeout\"></stringProp>\n" +
            "          <stringProp name=\"HTTPSampler.response_timeout\"></stringProp>\n" +
            "        </HTTPSamplerProxy>\n" +
            "        <hashTree/>\n" +
            "      </hashTree>\n" +
            "      <ThreadGroup guiclass=\"ThreadGroupGui\" testclass=\"ThreadGroup\" testname=\"test02线程组\" enabled=\"false\">\n" +
            "        <stringProp name=\"ThreadGroup.num_threads\">4</stringProp>\n" +
            "        <boolProp name=\"ThreadGroup.scheduler\">false</boolProp>\n" +
            "        <boolProp name=\"ThreadGroup.same_user_on_next_iteration\">true</boolProp>\n" +
            "        <elementProp name=\"ThreadGroup.main_controller\" elementType=\"LoopController\" guiclass=\"LoopControlPanel\" testclass=\"LoopController\" testname=\"循环控制器\" enabled=\"true\">\n" +
            "          <boolProp name=\"LoopController.continue_forever\">false</boolProp>\n" +
            "          <stringProp name=\"LoopController.loops\">200</stringProp>\n" +
            "        </elementProp>\n" +
            "        <stringProp name=\"ThreadGroup.delay\"></stringProp>\n" +
            "        <stringProp name=\"ThreadGroup.duration\"></stringProp>\n" +
            "        <stringProp name=\"ThreadGroup.on_sample_error\">continue</stringProp>\n" +
            "        <stringProp name=\"ThreadGroup.ramp_time\"></stringProp>\n" +
            "      </ThreadGroup>\n" +
            "      <hashTree/>\n" +
            "      <HeaderManager guiclass=\"HeaderPanel\" testclass=\"HeaderManager\" testname=\"HTTP信息头管理器\" enabled=\"false\">\n" +
            "        <collectionProp name=\"HeaderManager.headers\">\n" +
            "          <elementProp name=\"\" elementType=\"Header\">\n" +
            "            <stringProp name=\"Header.name\">traceId</stringProp>\n" +
            "            <stringProp name=\"Header.value\">${__GenerateAllSampled()}</stringProp>\n" +
            "          </elementProp>\n" +
            "        </collectionProp>\n" +
            "      </HeaderManager>\n" +
            "      <hashTree/>\n" +
            "      <BackendListener guiclass=\"BackendListenerGui\" testclass=\"BackendListener\" testname=\"后端监听器\" enabled=\"false\">\n" +
            "        <elementProp name=\"arguments\" elementType=\"Arguments\" guiclass=\"ArgumentsPanel\" testclass=\"Arguments\" enabled=\"true\">\n" +
            "          <collectionProp name=\"Arguments.arguments\">\n" +
            "            <elementProp name=\"graphiteMetricsSender\" elementType=\"Argument\">\n" +
            "              <stringProp name=\"Argument.name\">graphiteMetricsSender</stringProp>\n" +
            "              <stringProp name=\"Argument.value\">org.apache.jmeter.visualizers.backend.graphite.TextGraphiteMetricsSender</stringProp>\n" +
            "              <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
            "            </elementProp>\n" +
            "            <elementProp name=\"graphiteHost\" elementType=\"Argument\">\n" +
            "              <stringProp name=\"Argument.name\">graphiteHost</stringProp>\n" +
            "              <stringProp name=\"Argument.value\"></stringProp>\n" +
            "              <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
            "            </elementProp>\n" +
            "            <elementProp name=\"graphitePort\" elementType=\"Argument\">\n" +
            "              <stringProp name=\"Argument.name\">graphitePort</stringProp>\n" +
            "              <stringProp name=\"Argument.value\">2003</stringProp>\n" +
            "              <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
            "            </elementProp>\n" +
            "            <elementProp name=\"rootMetricsPrefix\" elementType=\"Argument\">\n" +
            "              <stringProp name=\"Argument.name\">rootMetricsPrefix</stringProp>\n" +
            "              <stringProp name=\"Argument.value\">jmeter.</stringProp>\n" +
            "              <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
            "            </elementProp>\n" +
            "            <elementProp name=\"summaryOnly\" elementType=\"Argument\">\n" +
            "              <stringProp name=\"Argument.name\">summaryOnly</stringProp>\n" +
            "              <stringProp name=\"Argument.value\">true</stringProp>\n" +
            "              <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
            "            </elementProp>\n" +
            "            <elementProp name=\"samplersList\" elementType=\"Argument\">\n" +
            "              <stringProp name=\"Argument.name\">samplersList</stringProp>\n" +
            "              <stringProp name=\"Argument.value\"></stringProp>\n" +
            "              <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
            "            </elementProp>\n" +
            "            <elementProp name=\"percentiles\" elementType=\"Argument\">\n" +
            "              <stringProp name=\"Argument.name\">percentiles</stringProp>\n" +
            "              <stringProp name=\"Argument.value\">90;95;99</stringProp>\n" +
            "              <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
            "            </elementProp>\n" +
            "          </collectionProp>\n" +
            "        </elementProp>\n" +
            "        <stringProp name=\"classname\">org.apache.jmeter.visualizers.backend.graphite.GraphiteBackendListenerClient</stringProp>\n" +
            "      </BackendListener>\n" +
            "      <hashTree/>\n" +
            "      <TestFragmentController guiclass=\"TestFragmentControllerGui\" testclass=\"TestFragmentController\" testname=\"test01线程组\" enabled=\"false\"/>\n" +
            "      <hashTree/>\n" +
            "      <TestFragmentController guiclass=\"TestFragmentControllerGui\" testclass=\"TestFragmentController\" testname=\"test02线程组\" enabled=\"false\"/>\n" +
            "      <hashTree>\n" +
            "        <HTTPSamplerProxy guiclass=\"HttpTestSampleGui\" testclass=\"HTTPSamplerProxy\" testname=\"HTTP请求\" enabled=\"true\">\n" +
            "          <elementProp name=\"HTTPsampler.Arguments\" elementType=\"Arguments\" guiclass=\"HTTPArgumentsPanel\" testclass=\"Arguments\" testname=\"用户定义的变量\" enabled=\"true\">\n" +
            "            <collectionProp name=\"Arguments.arguments\">\n" +
            "              <elementProp name=\"param\" elementType=\"HTTPArgument\">\n" +
            "                <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
            "                <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
            "                <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
            "                <stringProp name=\"Argument.name\">param</stringProp>\n" +
            "                <stringProp name=\"Argument.value\">12321321321</stringProp>\n" +
            "              </elementProp>\n" +
            "              <elementProp name=\"time\" elementType=\"HTTPArgument\">\n" +
            "                <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
            "                <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
            "                <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
            "                <stringProp name=\"Argument.name\">time</stringProp>\n" +
            "                <stringProp name=\"Argument.value\">2</stringProp>\n" +
            "              </elementProp>\n" +
            "            </collectionProp>\n" +
            "          </elementProp>\n" +
            "          <stringProp name=\"HTTPSampler.domain\">192.168.1.63</stringProp>\n" +
            "          <stringProp name=\"HTTPSampler.port\">8218</stringProp>\n" +
            "          <stringProp name=\"HTTPSampler.protocol\">http</stringProp>\n" +
            "          <stringProp name=\"HTTPSampler.contentEncoding\"></stringProp>\n" +
            "          <stringProp name=\"HTTPSampler.path\">/pt/test</stringProp>\n" +
            "          <stringProp name=\"HTTPSampler.method\">POST</stringProp>\n" +
            "          <boolProp name=\"HTTPSampler.follow_redirects\">true</boolProp>\n" +
            "          <boolProp name=\"HTTPSampler.auto_redirects\">false</boolProp>\n" +
            "          <boolProp name=\"HTTPSampler.use_keepalive\">true</boolProp>\n" +
            "          <boolProp name=\"HTTPSampler.DO_MULTIPART_POST\">false</boolProp>\n" +
            "          <stringProp name=\"HTTPSampler.embedded_url_re\"></stringProp>\n" +
            "          <stringProp name=\"HTTPSampler.connect_timeout\"></stringProp>\n" +
            "          <stringProp name=\"HTTPSampler.response_timeout\"></stringProp>\n" +
            "        </HTTPSamplerProxy>\n" +
            "        <hashTree/>\n" +
            "      </hashTree>\n" +
            "    </hashTree>\n" +
            "  </hashTree>\n" +
            "</jmeterTestPlan>\n";
}
