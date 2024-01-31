package fx.redbox.repository.request;

import fx.redbox.entity.donorCards.Request;
import fx.redbox.entity.donorCards.RequestForm;

import java.util.List;

public interface RequestRepository {

    Request saveRequest(Request request, RequestForm requestForm);

    Request getRequestById(String requestId);

    List<Request> getAllRequests();

    void acceptRequest(String requestId);

    void rejectRequest(String requestId, String rejectionReason);

    void cancelRequest(String requestId);

}
