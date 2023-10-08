import grpc
import google.protobuf.empty_pb2

from sales_proto_definition.sales_pb2_grpc import SaleServiceStub


class ApiClient:
    def __init__(self, target):
        channel = grpc.insecure_channel(target)
        self.client = SaleServiceStub(channel)

    def getStatistic(self):
        return self.client.getStatistics(google.protobuf.empty_pb2.Empty())

    def importSales(self, sales: list):
        return self.client.importSales(sales)
