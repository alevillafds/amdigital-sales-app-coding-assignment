import grpc
import google.protobuf.empty_pb2

from sales_proto_definition.sales_pb2_grpc import SaleServiceStub


class ApiClient:
    def __init__(self, target):
        channel = grpc.insecure_channel(target)
        self.client = SaleServiceStub(channel)

    def getStatistic(self):
        response = self.client.getStatistics(google.protobuf.empty_pb2.Empty())
        return response

    # def sayHello(self, name):
    #     response = self.client.sayHello(HelloRequest(name=name))
    #     return response.message

    # def getAll(self, length):
    #     response = self.client.getAll(ApiRequest(length=length))
    #     return response.items

    # def getStream(self, length):
    #     response = self.client.getStream(ApiRequest(length=length))
    #     return response
