namespace java cn.chinaunicom.pinpoint.thrift.dto

struct TStressTestAgentData {
    1: i8 dataType;
    2: string stringValue;
    3: string hostIp;
    4: string version;
    5: string charset;
    6: string userAppKey;
    7: string tenantAppKey;
    8: string userId;
    9: string envCode;
    10: string agentExpand;
}

struct TStressTestTraceData {
    1: string traceId;
    2: i64 timestamp;
    3: string agentId;
    4: string invokeId;
    5: i8 invokeType;
    6: string appName;
    7: i32 cost;
    8: string middlewareName;
    9: string serviceName;
    10: string methodName;
    11: string resultCode;
    12: bool pressureTest;
    13: bool debugTest;
    14: bool entrance;
    15: bool server;
    16: string entranceId;
    17: optional string upAppName;
    18: optional string remoteIp;
    19: optional i32 port;
}

struct TStressTestTracePayloadData {
    1: string traceId;
    2: i64 timestamp;
    3: string agentId;
    4: string invokeId;
    5: i8 invokeType;
    6: bool pressureTest;
    7: bool entrance;
    8: bool server;
    9: optional string appName;
    10: optional string middlewareName;
    11: optional string serviceName;
    12: optional string methodName;
    13: optional string request;
    14: optional string response;
    15: optional string callbackMsg;
    16: optional i32 requestSize;
    17: optional i32 responseSize;
    18: optional string ext;
    19: optional string rpcContent;
}

struct TStressTestAgentHeartbeatData {
    1: string agentId;
    2: optional string simulatorVersion;
    3: optional string address;
    4: string appName;
    5: optional i32 pid;
    6: optional string agentLanguage;
    7: i64 userId;
    8: optional string agentStatus;
    9: optional string jvmArgsCheck;
    10: optional string jdk;
    11: optional string jvmArgs;
    12: string envCode;
    13: optional string host;
    14: optional string name;
    15: optional string agentVersion;
    16: string tenantAppKey;
    17: optional string serviceName;
    18: optional string md5;
    19: bool status;
    20: bool moduleLoadResult;
    21: optional string errorCode;
    22: optional string errorMsg;
    23: optional i32 port;
}